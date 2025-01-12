package com.foke.together.domain.interactor

import android.content.Context
import androidx.annotation.IntRange
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import com.foke.together.domain.output.InternalCameraRepositoryInterface
import javax.inject.Inject

class GetInternalCameraPreviewUseCase @Inject constructor(
    private val internalCameraRepository: InternalCameraRepositoryInterface,
) {
    suspend operator fun invoke(
        context: Context,
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner,
        cameraSelector: CameraSelector,
        imageAnalysis: ImageAnalysis?,
        @IntRange(from = 0, to = 2) captureMode: Int,
        @IntRange(from = 0, to = 3) flashMode: Int,
        @IntRange(from = -1, to = 1) aspectRatio: Int
    ) = internalCameraRepository.showCameraPreview(
        context = context,
        lifecycleOwner = lifecycleOwner,
        previewView = previewView,
        selector = cameraSelector,
        imageAnalysis = imageAnalysis,
        captureMode = captureMode,
        flashMode = flashMode,
        aspectRatio = aspectRatio
    )
}