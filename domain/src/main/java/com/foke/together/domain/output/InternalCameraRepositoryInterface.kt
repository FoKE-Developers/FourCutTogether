package com.foke.together.domain.output

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.annotation.IntRange
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.Flow

interface InternalCameraRepositoryInterface {

    fun getCapturedImageUri() : Flow<Uri?>
    suspend fun capture(
        context: Context,
        fileName : String,
    )
    suspend fun initial(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        previewView : PreviewView,
        selector : CameraSelector,
        @IntRange(from = 0, to = 2) captureMode: Int,
        @IntRange(from = 0, to = 3) flashMode: Int,
        imageAnalyzer: ImageAnalysis.Analyzer?,
    )
    suspend fun release(context: Context)
}