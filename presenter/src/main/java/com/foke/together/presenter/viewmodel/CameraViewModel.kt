package com.foke.together.presenter.viewmodel

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foke.together.util.AppLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
): ViewModel() {
    // TODO: add viewmodel code here
    private val _progressState = mutableFloatStateOf(1f)
    val progressState: Float by _progressState
    private val _captureCount = mutableIntStateOf(1)
    val captureCount: Int by _captureCount
    private var captureTimer: CountDownTimer? = null
    fun setCaptureTimer(nextNavigate: () -> Unit) {
        captureTimer = object : CountDownTimer(5000, 10) {
            override fun onTick(millisUntilFinished: Long) {
                _progressState.floatValue = 1f - (millisUntilFinished.toFloat() / 5000)
            }
            override fun onFinish() {
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

}