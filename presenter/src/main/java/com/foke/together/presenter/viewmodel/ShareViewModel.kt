package com.foke.together.presenter.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foke.together.domain.interactor.GeneratePhotoFrameUseCaseV1
import com.foke.together.domain.interactor.GetQRCodeUseCase
import com.foke.together.domain.interactor.entity.Status
import com.foke.together.domain.interactor.session.ClearSessionUseCase
import com.foke.together.domain.interactor.session.GetCurrentSessionUseCase
import com.foke.together.domain.interactor.session.UpdateSessionStatusUseCase
import com.foke.together.domain.interactor.web.GetDownloadUrlUseCase
import com.foke.together.domain.interactor.web.UploadFileUseCase
import com.foke.together.util.AppLog
import com.foke.together.util.AppPolicy
import com.foke.together.util.ImageFileUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getQRCodeUseCase: GetQRCodeUseCase,
    private val getDownloadUrlUseCase: GetDownloadUrlUseCase,
    private val uploadFileUseCase: UploadFileUseCase,
    private val generatePhotoFrameUseCaseV1: GeneratePhotoFrameUseCaseV1,
    private val getCurrentSessionUseCase: GetCurrentSessionUseCase,
    private val updateSessionStatusUseCase: UpdateSessionStatusUseCase,
    private val clearSessionUseCase: ClearSessionUseCase
): ViewModel() {
    val qrCodeBitmap = MutableStateFlow<Bitmap?>(null)
    val singleImageUri: Uri = generatePhotoFrameUseCaseV1.getFinalSingleImageUri()
    val twoImageUri: Uri = generatePhotoFrameUseCaseV1.getFinalTwoImageUri()

    init {
        viewModelScope.launch {
            generateQRcode()
        }
    }

    fun downloadImage(): Result<Unit> {
        return getCurrentSessionUseCase()?.let { session ->
            val imageBitmap = ImageFileUtil.getBitmapFromUri(context, singleImageUri)
            viewModelScope.launch {
                ImageFileUtil.saveBitmapToStorage(
                    context,
                    imageBitmap,
                    session.sessionId.toString()
                )
            }
            Result.success(Unit)
        } ?: run {
            Result.failure(Exception("invalid session id"))
        }
    }

    private suspend fun generateQRcode() {
        getCurrentSessionUseCase()?.let { session ->
            val sessionKey = session.sessionId.toString()

            val result = uploadFileUseCase(sessionKey, singleImageUri.toFile())
            AppLog.d(TAG, "generateQRcode" ,"result: $result")

            val downloadUrl: String = getDownloadUrlUseCase(sessionKey).getOrElse { AppPolicy.WEB_SERVER_URL }
            if (AppPolicy.isDebugMode) {
                AppLog.e(TAG, "generateQRcode", "sessionKey: $sessionKey")
                AppLog.e(TAG, "generateQRcode", "downloadUrl: $downloadUrl")
            }
            qrCodeBitmap.value =  getQRCodeUseCase(sessionKey, downloadUrl).getOrNull()
        }
    }

    fun shareImage() {
        val contentUri = FileProvider.getUriForFile(
            context,
            "com.foke.together.fileprovider",
            singleImageUri.toFile()
        )
        ImageFileUtil.shareUri(context, contentUri)
    }

    fun printImage(activityContext: Context) {
        ImageFileUtil.printFromUri(activityContext,twoImageUri)
    }

    fun updateSessionStatus() {
        updateSessionStatusUseCase(Status.SHARE)
    }

    fun closeSession() {
        clearSessionUseCase()
    }

    companion object {
        private val TAG = ShareViewModel::class.java.simpleName
    }
}