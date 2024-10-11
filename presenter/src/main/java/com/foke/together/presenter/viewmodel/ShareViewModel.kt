package com.foke.together.presenter.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foke.together.domain.interactor.GeneratePhotoFrameUseCase
import com.foke.together.domain.interactor.GetQRCodeUseCase
import com.foke.together.domain.interactor.web.GetDownloadUrlUseCase
import com.foke.together.domain.interactor.web.SessionKeyUseCase
import com.foke.together.util.AppPolicy
import com.foke.together.util.ImageFileUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
    val qrCodeBitmap: Flow<Bitmap> = flow{
        val qrCodeBitmap = generateQRcode()
        emit(qrCodeBitmap)
    }

    val singleImageUri: Uri = generatePhotoFrameUseCase.getFinalSingleImageUri()

    val twoImageUri: Uri = generatePhotoFrameUseCase.getFinalTwoImageUri()

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

    suspend fun generateQRcode(): Bitmap {
        val sessionKey = sessionKeyUseCase.getSessionKey()
        val downloadUrl:String = getDownloadUrlUseCase(sessionKey).toString()
        if(downloadUrl.contains("Failure")){
            return Bitmap.createBitmap(
                1,1,Bitmap.Config.ARGB_8888)
        }
        return getQRCodeUseCase(sessionKey, downloadUrl)
            // return a empty bitmap if the qr code generation fails
                .getOrElse(
                    return Bitmap.createBitmap(
                        1,1,Bitmap.Config.ARGB_8888)
                )
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
}