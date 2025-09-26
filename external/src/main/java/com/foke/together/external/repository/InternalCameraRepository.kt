package com.foke.together.external.repository

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.IntRange
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.FLASH_MODE_ON
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.foke.together.domain.output.InternalCameraRepositoryInterface
import com.foke.together.util.AppLog
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternalCameraRepository @Inject constructor(
): InternalCameraRepositoryInterface{
    private lateinit var cameraController: LifecycleCameraController
    private lateinit var imageCapture: ImageCapture

    private var imageBitmap : Bitmap? = null
    override suspend fun capture(
        context: Context,
        fileName : String,
    ) {
        if(::cameraController.isInitialized) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "${fileName}.jpg")
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/4cuts/backup")
            }
            val outputOptions = ImageCapture.OutputFileOptions.Builder(
                context.contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues
            ).build()

            cameraController.takePicture(
                outputOptions, ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        if (outputFileResults.savedUri != null) {
                            imageBitmap = MediaStore.Images.Media.getBitmap(
                                context.contentResolver,
                                outputFileResults.savedUri
                            )
                        }
                    }
                    override fun onError(exception: ImageCaptureException) {
                        AppLog.e(TAG, "onError", "Photo capture failed: ${exception.message}")
                    }
                })
        }
        else{
            throw Exception("cameraController is not initialized")
        }
    }

    override suspend fun initial(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        previewView : PreviewView,
        selector : CameraSelector,
        @IntRange(from = 0, to = 2) captureMode: Int,
        @IntRange(from = 0, to = 3) flashMode: Int,
        imageAnalyzer: ImageAnalysis.Analyzer?,
    ) {
        try{
            cameraController = LifecycleCameraController(context)
            cameraController.cameraSelector = selector
            if(imageAnalyzer != null){
                cameraController.setImageAnalysisAnalyzer(ContextCompat.getMainExecutor(context), imageAnalyzer)
            }
            imageCapture = ImageCapture.Builder()
                .setCaptureMode(captureMode)
                .setFlashMode(flashMode)
                .build()
            cameraController.imageCaptureIoExecutor = ContextCompat.getMainExecutor(context)
            cameraController.imageCaptureMode = captureMode
            cameraController.imageCaptureFlashMode = flashMode
            cameraController.bindToLifecycle(lifecycleOwner)
            previewView.controller = cameraController
        }
        catch (e: Exception){
            AppLog.e(TAG,"showCameraPreview", e.message!!)
        }
    }

    override suspend fun release(context: Context) {
        try{
            if(::cameraController.isInitialized) {
                cameraController.unbind()
            }
        }
        catch (e: Exception){
            AppLog.e(TAG,"release", e.message!!)
        }
    }


    companion object {
        private val TAG = InternalCameraRepository::class.java.simpleName
    }
}