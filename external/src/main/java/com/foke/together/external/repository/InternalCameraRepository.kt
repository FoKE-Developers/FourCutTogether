package com.foke.together.external.repository

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.IntRange
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.FLASH_MODE_ON
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.foke.together.domain.output.InternalCameraRepositoryInterface
import com.foke.together.util.AppLog
import javax.inject.Inject

class InternalCameraRepository @Inject constructor(
): InternalCameraRepositoryInterface{
    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraProvider : ProcessCameraProvider
    override suspend fun capture(
        context: Context,
    ): Result<Bitmap> {
        var imageBitmap : Bitmap? = null
        imageCapture.takePicture(
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(imageProxy: ImageProxy) {
                    imageBitmap = imageProxy.toBitmap()
                }

                override fun onError(exception: ImageCaptureException) {
                    imageBitmap = null
                }
            }
        )
        return if(imageBitmap != null){
            Result.success(imageBitmap!!)
        } else{
            Result.failure(Exception("Unknown error"))
        }
    }

    override suspend fun showCameraPreview(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        previewView: PreviewView,
        selector : CameraSelector,
        imageAnalysis: ImageAnalysis?,
        @IntRange(from = 0, to = 2) captureMode: Int,
        @IntRange(from = 0, to = 3) flashMode: Int,
        @IntRange(from = -1, to = 1) aspectRatio: Int
    ) {
        try{
            cameraProvider = ProcessCameraProvider.getInstance(context).get()
            cameraProvider.unbindAll()
            val preview = Preview.Builder().build().also {
                it.surfaceProvider = previewView.surfaceProvider
            }
            imageCapture = ImageCapture.Builder()
                .setCaptureMode(captureMode)
                .setFlashMode(flashMode)
                .setTargetAspectRatio(aspectRatio)
                .build()

            if(imageAnalysis != null) {
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    selector,
                    preview,
                    imageAnalysis,
                    imageCapture
                )
            }
            else {
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    selector,
                    preview,
                    imageCapture
                )
            }
        }
        catch (e: Exception){
            AppLog.e(TAG,"showCameraPreview", e.message!!)
        }
    }

    companion object {
        private val TAG = InternalCameraRepository::class.java.simpleName
    }
}