package com.foke.together.domain.interactor

import android.content.Context
import androidx.annotation.IntRange
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import com.foke.together.domain.output.InternalCameraRepositoryInterface
import javax.inject.Inject

class InternalCameraUseCase @Inject constructor(
    private val internalCameraRepository: InternalCameraRepositoryInterface,
) {
    suspend fun initial(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        previewView : PreviewView,
        cameraSelector: CameraSelector,
        imageAnalyzer: ImageAnalysis.Analyzer?,
        @IntRange(from = 0, to = 2) captureMode: Int,
        @IntRange(from = 0, to = 3) flashMode: Int,
    ) = internalCameraRepository.initial(
        context = context,
        lifecycleOwner = lifecycleOwner,
        captureMode = captureMode,
        flashMode = flashMode,
        selector = cameraSelector,
        imageAnalyzer = imageAnalyzer,
        previewView = previewView,
    )

    suspend fun capture(
        context: Context,
        fileName : String,
    ) = internalCameraRepository.capture(
        context = context,
        fileName = fileName
    )
    suspend fun release(
        context: Context
    ) = internalCameraRepository.release(context)
}