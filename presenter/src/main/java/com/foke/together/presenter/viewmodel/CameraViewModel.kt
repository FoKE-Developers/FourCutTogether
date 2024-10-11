package com.foke.together.presenter.viewmodel

import android.content.Context
import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foke.together.domain.interactor.CaptureWithExternalCameraUseCase
import com.foke.together.domain.interactor.GeneratePhotoFrameUseCase
import com.foke.together.domain.interactor.GetExternalCameraPreviewUrlUseCase
import com.foke.together.domain.interactor.web.SessionKeyUseCase
import com.foke.together.util.AppPolicy
import com.foke.together.util.AppPolicy.CAPTURE_INTERVAL
import com.foke.together.util.AppPolicy.COUNTDOWN_INTERVAL
import com.foke.together.util.SoundUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    getExternalCameraPreviewUrlUseCase: GetExternalCameraPreviewUrlUseCase,
    private val captureWithExternalCameraUseCase: CaptureWithExternalCameraUseCase,
    private val generatePhotoFrameUseCase: GeneratePhotoFrameUseCase,
    private val sessionKeyUseCase: SessionKeyUseCase
): ViewModel() {
    val externalCameraIP = getExternalCameraPreviewUrlUseCase()

    // TODO: progress State를 Flow로 구현하기
    private val _progressState = mutableFloatStateOf(1f)
    val progressState: Float by _progressState
    private val _captureCount = mutableIntStateOf(1)
    val captureCount: Int by _captureCount
    private var captureTimer: CountDownTimer? = null
    private var mTimerState = false
    fun setCaptureTimer(
        graphicsLayer: GraphicsLayer,
        nextNavigate: () -> Unit
    ) {
        viewModelScope.launch {
            generatePhotoFrameUseCase.clearCapturedImageList()
        }
        captureTimer = object : CountDownTimer(CAPTURE_INTERVAL, COUNTDOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                _progressState.floatValue = 1f - (millisUntilFinished.toFloat() / CAPTURE_INTERVAL)
            }
            override fun onFinish() {
                viewModelScope.launch {
                    SoundUtil.getCameraSound(context = context )
                    val bitmap = graphicsLayer.toImageBitmap().asAndroidBitmap()

                    // TODO: 현재 External 실패 시, 스크린 캡쳐 화면을 사용하도록 구성함
                    val fileName = "${AppPolicy.CAPTURED_FOUR_CUT_IMAGE_NAME}_${_captureCount.value}"
                    captureWithExternalCameraUseCase(fileName)
                        .onFailure {
                            generatePhotoFrameUseCase.saveGraphicsLayerImage(bitmap, fileName)
                        }

                    _progressState.floatValue = 1f
                    if (_captureCount.intValue < AppPolicy.CAPTURE_COUNT) {
                        _captureCount.intValue += 1
                        mTimerState = false
                    } else {
                        sessionKeyUseCase.setSessionKey()
                        stopCaptureTimer()
                        _captureCount.intValue = 1
                        nextNavigate()
                    }
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
}