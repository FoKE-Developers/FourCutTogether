package com.foke.together.presenter.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foke.together.domain.interactor.GeneratePhotoFrameUseCase
import com.foke.together.domain.interactor.GetQRCodeUseCase
import com.foke.together.domain.interactor.web.GetDownloadUrlUseCase
import com.foke.together.domain.interactor.web.SessionKeyUseCase
import com.foke.together.util.AppLog
import com.foke.together.util.AppPolicy
import com.foke.together.util.ImageFileUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getQRCodeUseCase: GetQRCodeUseCase,
    private val getDownloadUrlUseCase: GetDownloadUrlUseCase,
    private val sessionKeyUseCase: SessionKeyUseCase,
    private val generatePhotoFrameUseCase: GeneratePhotoFrameUseCase
): ViewModel() {
    var qrCodeBitmap by mutableStateOf<Bitmap?>(null)
    val singleImageUri: Uri = generatePhotoFrameUseCase.getFinalSingleImageUri()
    val twoImageUri: Uri = generatePhotoFrameUseCase.getFinalTwoImageUri()

    init {
        viewModelScope.launch {
            generateQRcode()
        }
    }

    fun downloadImage() {
        val imageBitmap = ImageFileUtil.getBitmapFromUri(context, singleImageUri)
        viewModelScope.launch {
            ImageFileUtil.saveBitmapToStorage(
                context,
                imageBitmap,
                AppPolicy.SINGLE_ROW_FINAL_IMAGE_NAME
            )
        }
    }

    private suspend fun generateQRcode() {
        val sessionKey = sessionKeyUseCase.getSessionKey()
        val downloadUrl: String = getDownloadUrlUseCase(sessionKey).getOrElse { "https://4cuts.store" }

        if (AppPolicy.isDebugMode) {
            AppLog.e(TAG, "generateQRcode", "sessionKey: $sessionKey")
            AppLog.e(TAG, "generateQRcode", "downloadUrl: $downloadUrl")
        }
        qrCodeBitmap =  getQRCodeUseCase(sessionKey, downloadUrl).getOrNull()
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

    companion object {
        private val TAG = ShareViewModel::class.java.simpleName
    }
}