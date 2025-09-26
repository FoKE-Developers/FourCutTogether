package com.foke.together.domain.output

import androidx.annotation.IntRange
import androidx.camera.core.CameraSelector
import com.foke.together.domain.interactor.entity.CameraSourceType
import com.foke.together.domain.interactor.entity.ExternalCameraIP
import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

interface AppPreferenceInterface {
    fun getCameraSourceType(): Flow<CameraSourceType>
    suspend fun setCameraSourceType(type: CameraSourceType)

    fun getExternalCameraIP(): Flow<ExternalCameraIP>
    suspend fun setExternalCameraIP(ip: ExternalCameraIP)

    fun getInternalCameraLensFacing(): Flow<Int>
    suspend fun setInternalCameraLensFacing(
        @IntRange(from = 0, to = 1) lensFacing: Int
    )

    fun getInterenalCameraFlashMode(): Flow<Int>
    suspend fun setInterenalCameraFlashMode(
        @IntRange(from = 0, to = 3) flashMode: Int
    )

    fun getInternalCameraCaptureMode(): Flow<Int>
    suspend fun setInternalCameraCaptureMode(
        @IntRange(from = 0, to = 2) captureMode: Int
    )

    fun getCaptureDuration(): Flow<Duration>

    suspend fun setCaptureDuration(
        duration: Duration
    )

    suspend fun clearAll()
}