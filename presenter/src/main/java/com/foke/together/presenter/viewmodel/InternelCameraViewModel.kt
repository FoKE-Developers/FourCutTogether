package com.foke.together.presenter.viewmodel

import android.content.Context
import android.os.CountDownTimer
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.view.PreviewView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foke.together.domain.interactor.GeneratePhotoFrameUseCaseV1
import com.foke.together.domain.interactor.GetInternalCameraCaptureModeUseCase
import com.foke.together.domain.interactor.GetInternalCameraFlashModeUseCase
import com.foke.together.domain.interactor.GetInternalCameraLensFacingUseCase
import com.foke.together.domain.interactor.InternalCameraUseCase
import com.foke.together.domain.interactor.session.GetCurrentSessionUseCase
import com.foke.together.presenter.screen.state.InternalCameraState
import com.foke.together.util.AppLog
import com.foke.together.util.AppPolicy
import com.foke.together.util.AppPolicy.CAPTURE_INTERVAL
import com.foke.together.util.AppPolicy.COUNTDOWN_INTERVAL
import com.foke.together.util.SoundUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InternelCameraViewModel @Inject constructor(
    private val internalCameraUseCase: InternalCameraUseCase,
    private val getInternalCameraLensFacingUseCase: GetInternalCameraLensFacingUseCase,
    private val getInternalCameraFlashModeUseCase: GetInternalCameraFlashModeUseCase,
    private val getInternalCameraCaptureModeUseCase: GetInternalCameraCaptureModeUseCase,
    private val getSessionUseCase : GetCurrentSessionUseCase,
    private val generatePhotoFrameUseCaseV1: GeneratePhotoFrameUseCaseV1,
): ViewModel() {

    // TODO(MutableStateFlow 로 처리하기)
    val captureCount = MutableStateFlow(1)
    val progressState = MutableStateFlow(1f)
    private var captureTimer: CountDownTimer? = null
    private var mTimerState = false
    private val cameraSelector = getInternalCameraLensFacingUseCase().map { cameraSelectorIdx ->
        CameraSelector.Builder()
            .requireLensFacing(cameraSelectorIdx)
            .build()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = CameraSelector.DEFAULT_FRONT_CAMERA
    )

    private val captureMode = getInternalCameraCaptureModeUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(subscribeDuration),
        initialValue = ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
    )

    private val flashMode = getInternalCameraFlashModeUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(subscribeDuration),
        initialValue = ImageCapture.FLASH_MODE_OFF
    )


    private val curFrame = getSessionUseCase()?.cutFrame ?: run  { throw Exception("invalid cut frame") }

    val state = InternalCameraState(
        aspectRatio = curFrame.photoPosition.first().width.toFloat() / curFrame.height.toFloat(),
        cutCount = curFrame.cutCount,
    )

    fun initial(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        previewView: PreviewView,
    ) = viewModelScope.launch {
        internalCameraUseCase.initial(
            context = context,
            lifecycleOwner = lifecycleOwner,
            cameraSelector = cameraSelector.value,
            imageAnalyzer = null,
            captureMode = captureMode.value,
            flashMode = flashMode.value,
            previewView = previewView
        )
    }

    fun release(
        context: Context,
    )= viewModelScope.launch{
        internalCameraUseCase.release(context)
    }

    fun capture(
        context: Context,
    ) = viewModelScope.launch {
        val fileName = "${AppPolicy.CAPTURED_FOUR_CUT_IMAGE_NAME}_${System.currentTimeMillis()}_${captureCount}"
        internalCameraUseCase.capture(context, fileName)
    }

    fun setCaptureTimer(
        context: Context,
        nextNavigate : () -> Unit
    ){
        if (AppPolicy.isNoCameraDebugMode) {
            nextNavigate()
            return
        }

        viewModelScope.launch {
            generatePhotoFrameUseCaseV1.clearCapturedImageList()
        }

        captureTimer = object : CountDownTimer(CAPTURE_INTERVAL, COUNTDOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                progressState.value = 1f - (millisUntilFinished.toFloat() / CAPTURE_INTERVAL)
            }
            override fun onFinish() {
                SoundUtil.getCameraSound(context = context )
                // TODO: 현재 External 실패 시, 스크린 캡쳐 화면을 사용하도록 구성함
                capture(context)
                progressState.value = 1f
                if (captureCount.value < AppPolicy.CAPTURE_COUNT) {
                    AppLog.d(TAG, "captureCount", "$captureCount")
                    captureCount.value += 1
                    start()
                } else {
                    AppLog.d(TAG, "captureCount", "$captureCount")
                    stopCaptureTimer()
                    captureCount.value = 1
                    nextNavigate()
                }
            }
        }
    }

    fun startCaptureTimer() = viewModelScope.launch{
        if(captureTimer != null){
            if(!mTimerState){
                mTimerState = true
                captureTimer!!.start()
            }
        }
    }

    fun stopCaptureTimer() = viewModelScope.launch{
        if(captureTimer != null){
            mTimerState = false
            captureTimer!!.cancel()
        }
    }

    companion object {
        const val TAG = "InternelCameraViewModel"
        private const val subscribeDuration = 1000L
    }
}