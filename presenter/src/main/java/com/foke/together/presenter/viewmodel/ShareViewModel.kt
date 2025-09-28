package com.foke.together.presenter.viewmodel

import android.content.Context
import android.net.Uri
import android.os.CountDownTimer
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foke.together.domain.interactor.GeneratePhotoFrameUseCaseV1
import com.foke.together.domain.interactor.entity.Status
import com.foke.together.domain.interactor.session.ClearSessionUseCase
import com.foke.together.domain.interactor.session.GetCurrentSessionUseCase
import com.foke.together.domain.interactor.session.UpdateSessionStatusUseCase
import com.foke.together.util.AppPolicy
import com.foke.together.util.ImageFileUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit

@HiltViewModel
class ShareViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getCurrentSessionUseCase: GetCurrentSessionUseCase,
    private val updateSessionStatusUseCase: UpdateSessionStatusUseCase,
    private val clearSessionUseCase: ClearSessionUseCase,
    generatePhotoFrameUseCaseV1: GeneratePhotoFrameUseCaseV1
): ViewModel() {
    val qrCodeBitmap = getCurrentSessionUseCase()?.qrCodeBitmap
    val singleImageUri: Uri = generatePhotoFrameUseCaseV1.getFinalSingleImageUri()
    val twoImageUri: Uri = generatePhotoFrameUseCaseV1.getFinalTwoImageUri()

    private var returnHomeTimer: CountDownTimer? = null

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

    fun shareImage(context: Context) {
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

    fun setupTimer(
        finished : () -> Unit
    ){
        returnHomeTimer = object : CountDownTimer(1.minutes.toLong(DurationUnit.MILLISECONDS), AppPolicy.COUNTDOWN_INTERVAL){
            override fun onFinish() {
                finished()
            }
            override fun onTick(millisUntilFinished: Long) {
                //
            }
        }
    }
    fun startTimer() {
        returnHomeTimer?.start()
    }

    fun closeTimer(

    ){
        returnHomeTimer?.cancel()
        returnHomeTimer = null
    }

    fun closeSession() {
        clearSessionUseCase()
    }

    companion object {
        private val TAG = ShareViewModel::class.java.simpleName
    }
}