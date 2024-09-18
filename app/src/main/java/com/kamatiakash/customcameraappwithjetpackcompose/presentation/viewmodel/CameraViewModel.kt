package com.kamatiakash.customcameraappwithjetpackcompose.presentation.viewmodel

import android.content.Context
import android.os.CountDownTimer
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamatiakash.customcameraappwithjetpackcompose.domain.repository.CustomCameraRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val repo: CustomCameraRepo
): ViewModel() {

    fun showCameraPreview(
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner
    ){
        viewModelScope.launch {
            repo.showCameraPreview(
                previewView,
                lifecycleOwner
            )
        }
    }

    fun captureAndSave(context: Context){
        viewModelScope.launch {
            repo.captureAndSaveImage(context)
        }
    }

    fun timerCapture(context: Context, time : Long){
            viewModelScope.launch {
                delay(1000L)
                val counter = object : CountDownTimer(time, 5000) {
                    override fun onTick(millisUntilFinished: Long) {
                        captureAndSave(context)
                    }

                    override fun onFinish() {
                        this.cancel()
                    }
                }
            }
    }

}