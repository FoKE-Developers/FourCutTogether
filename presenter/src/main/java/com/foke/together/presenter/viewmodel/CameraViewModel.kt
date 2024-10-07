package com.foke.together.presenter.viewmodel

import android.content.Context
import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foke.together.util.AppLog
import com.foke.together.util.BitmapUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    @ApplicationContext private val context: Context
): ViewModel() {
    // TODO: progress State를 Flow로 구현하기
    private val _progressState = mutableFloatStateOf(1f)
    val progressState: Float by _progressState
    private val _captureCount = mutableIntStateOf(1)
    val captureCount: Int by _captureCount
    private var captureTimer: CountDownTimer? = null
    fun setCaptureTimer(
        graphicsLayer: GraphicsLayer,
        nextNavigate: () -> Unit
    ) {
        captureTimer = object : CountDownTimer(5000, 10) {
            override fun onTick(millisUntilFinished: Long) {
                _progressState.floatValue = 1f - (millisUntilFinished.toFloat() / 5000)
            }
            override fun onFinish() {
                captureImage(graphicsLayer)
                _progressState.floatValue = 1f
                if(_captureCount.intValue < 4){
                    _captureCount.intValue += 1
                    startCaptureTimer()
                }
                else {
                    stopCaptureTimer()
                    _captureCount.intValue = 1
                    nextNavigate()
                }
            }
        }
    }

    fun startCaptureTimer() = viewModelScope.launch{
        if(captureTimer != null) captureTimer!!.start()
    }
    fun stopCaptureTimer() = viewModelScope.launch{
        if(captureTimer != null) captureTimer!!.cancel()
    }

    fun captureImage(graphicsLayer: GraphicsLayer) = viewModelScope.launch {
        BitmapUtil.saveBitmap(graphicsLayer, context,"together_${captureCount}")
    }

}