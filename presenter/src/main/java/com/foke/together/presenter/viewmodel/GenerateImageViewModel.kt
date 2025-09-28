package com.foke.together.presenter.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foke.together.domain.interactor.GenerateImageFromViewUseCase
import com.foke.together.domain.interactor.GeneratePhotoFrameUseCaseV1
import com.foke.together.domain.interactor.GetCameraSourceTypeUseCase
import com.foke.together.domain.interactor.GetQRCodeUseCase
import com.foke.together.domain.interactor.entity.CutFrame
import com.foke.together.domain.interactor.entity.Status
import com.foke.together.domain.interactor.session.GetCurrentSessionUseCase
import com.foke.together.domain.interactor.session.UpdateSessionStatusUseCase
import com.foke.together.domain.interactor.web.GetDownloadUrlUseCase
import com.foke.together.domain.interactor.web.UploadFileUseCase
import com.foke.together.util.AppLog
import com.foke.together.util.AppPolicy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GenerateImageViewModel @Inject constructor(
    getCameraSourceTypeUseCase : GetCameraSourceTypeUseCase,
    private val getCurrentSessionUseCase: GetCurrentSessionUseCase,
    private val generateImageFromViewUseCase: GenerateImageFromViewUseCase,
    private val generatePhotoFrameUseCaseV1: GeneratePhotoFrameUseCaseV1,
    private val updateSessionStatusUseCase: UpdateSessionStatusUseCase,
    private val getQRCodeUseCase: GetQRCodeUseCase,
    private val getDownloadUrlUseCase: GetDownloadUrlUseCase,
    private val uploadFileUseCase: UploadFileUseCase,
): ViewModel() {

    init {
        updateSessionStatusUseCase(Status.GENERATE_PHOTO)
    }

    var cutFrame: CutFrame = getCurrentSessionUseCase()?.cutFrame ?: run { throw Exception("invalid cut frame") }
    var qrImageBitmap: Bitmap? = null

    val imageUri = getCameraSourceTypeUseCase().map{ sourceType ->
        generatePhotoFrameUseCaseV1.getCapturedImageListUri(sourceType)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = listOf()
    )

    suspend fun generateImageForUpload(graphicsLayer: GraphicsLayer) {
        generateImageFromViewUseCase(graphicsLayer, isCutFrameForPrint = false)
    }

    suspend fun generateImage(graphicsLayer: GraphicsLayer) {
        val finalCachedImageUri = generateImageFromViewUseCase(graphicsLayer, isCutFrameForPrint = false)
        val finalExternalImageUri = generateImageFromViewUseCase(graphicsLayer, isCutFrameForPrint = true)
        AppLog.d(TAG, "generateImage" ,"finalCachedImageUri: $finalCachedImageUri")
        AppLog.d(TAG, "generateImageForPrint" ,"finalExternalImageUri: $finalExternalImageUri")
    }

    suspend fun generateQrCode() {
        getCurrentSessionUseCase()?.let { session ->
            val sessionKey = session.sessionId.toString()
            val downloadUrl: String = getDownloadUrlUseCase(sessionKey).getOrElse { AppPolicy.WEB_SERVER_URL }
            getQRCodeUseCase(sessionKey, downloadUrl).getOrNull()?.let {
                updateSessionStatusUseCase(it)
                qrImageBitmap = it
            }
            if (AppPolicy.isDebugMode) {
                AppLog.e(TAG, "generateQrCode", "sessionKey: $sessionKey")
                AppLog.e(TAG, "generateQrCode", "downloadUrl: $downloadUrl")
            }
        }
    }

    suspend fun uploadImage() {
        getCurrentSessionUseCase()?.let { session ->
            val sessionKey = session.sessionId.toString()

            val singleImageUri: Uri = generatePhotoFrameUseCaseV1.getFinalSingleImageUri()
            val result = uploadFileUseCase(sessionKey, singleImageUri.toFile())
            AppLog.d(TAG, "generateQrCode" ,"result: $result")
        }
    }

    companion object {
        private val TAG = GenerateImageViewModel::class.java.simpleName
    }
}