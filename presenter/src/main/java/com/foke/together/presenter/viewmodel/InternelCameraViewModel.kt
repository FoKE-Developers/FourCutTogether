package com.foke.together.presenter.viewmodel

import android.content.Context
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foke.together.domain.interactor.CaptureWithInternalCameraUseCase
import com.foke.together.domain.interactor.GetInternalCameraAspectRatioUseCase
import com.foke.together.domain.interactor.GetInternalCameraCaptureModeUseCase
import com.foke.together.domain.interactor.GetInternalCameraFlashModeUseCase
import com.foke.together.domain.interactor.GetInternalCameraLensFacingUseCase
import com.foke.together.domain.interactor.GetInternalCameraPreviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InternelCameraViewModel @Inject constructor(
    private val getInternalCameraPreviewUseCase: GetInternalCameraPreviewUseCase,
    private val getInternalCameraLensFacingUseCase: GetInternalCameraLensFacingUseCase,
    private val getInternalCameraFlashModeUseCase: GetInternalCameraFlashModeUseCase,
    private val getInternalCameraCaptureModeUseCase: GetInternalCameraCaptureModeUseCase,
    private val getInternalCameraAspectRatioUseCase: GetInternalCameraAspectRatioUseCase,
    private val captureWithInternalCameraUseCase: CaptureWithInternalCameraUseCase
): ViewModel() {
    private val cameraSelector = getInternalCameraLensFacingUseCase().stateIn(
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
        initialValue = ImageCapture.FLASH_MODE_ON
    )

    private val aspectRatio = getInternalCameraAspectRatioUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(subscribeDuration),
        initialValue = AspectRatio.RATIO_DEFAULT
    )

    fun preview(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        previewView: PreviewView,
    ) = viewModelScope.launch {
        getInternalCameraPreviewUseCase(
            context,
            previewView,
            lifecycleOwner,
            cameraSelector.value,
            null,
            captureMode.value,
            flashMode.value,
            aspectRatio.value
        )

    }

    companion object {
        const val TAG = "InternelCameraViewModel"
        private const val subscribeDuration = 1000L
    }
}