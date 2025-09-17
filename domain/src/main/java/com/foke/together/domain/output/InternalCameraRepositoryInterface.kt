package com.foke.together.domain.output

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.IntRange
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner

interface InternalCameraRepositoryInterface {
    suspend fun capture(context: Context): Result<Bitmap>
    suspend fun showCameraPreview(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        previewView: PreviewView,
        selector : CameraSelector,
        imageAnalysis: ImageAnalysis?,
        @IntRange(from = 0, to = 2) captureMode: Int,
        @IntRange(from = 0, to = 3) flashMode: Int,
        @IntRange(from = -1, to = 1) aspectRatio: Int
    )
}