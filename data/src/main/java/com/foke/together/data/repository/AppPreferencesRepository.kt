package com.foke.together.data.repository

import androidx.annotation.IntRange
import androidx.camera.core.CameraSelector
import androidx.datastore.core.DataStore
import com.foke.together.AppPreferences
import com.foke.together.CameraSource
import com.foke.together.domain.interactor.entity.CameraSourceType
import com.foke.together.domain.interactor.entity.ExternalCameraIP
import com.foke.together.domain.output.AppPreferenceInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// TODO: datasource -> local / remote 기준으로 module 구분하는게 어떨지?
class AppPreferencesRepository @Inject constructor(
    private val appPreferences: DataStore<AppPreferences>
): AppPreferenceInterface {
    private val appPreferencesFlow: Flow<AppPreferences> = appPreferences.data

    override fun getCameraSourceType(): Flow<CameraSourceType> =
        appPreferencesFlow.map {
            it.cameraSource?.run {
                when (this) {
                    CameraSource.CAMERA_SOURCE_INTERNAL -> CameraSourceType.INTERNAL
                    CameraSource.CAMERA_SOURCE_EXTERNAL -> CameraSourceType.EXTERNAL
                    CameraSource.UNRECOGNIZED -> null
                }
            } ?: CameraSourceType.INTERNAL // set to default INTERNAL
        }

    override suspend fun setCameraSourceType(type: CameraSourceType) {
        when (type) {
            CameraSourceType.INTERNAL -> CameraSource.CAMERA_SOURCE_INTERNAL
            CameraSourceType.EXTERNAL -> CameraSource.CAMERA_SOURCE_EXTERNAL
        }.apply {
            appPreferences.updateData {
                it.toBuilder()
                    .setCameraSource(this)
                    .build()
            }
        }
    }

    override fun getExternalCameraIP(): Flow<ExternalCameraIP> =
        appPreferencesFlow.map {
            ExternalCameraIP(it.externalCameraIp)
        }

    override suspend fun setExternalCameraIP(ip: ExternalCameraIP) {
        appPreferences.updateData {
            it.toBuilder()
                .setExternalCameraIp(ip.address)
                .build()
        }
    }

    override fun getInternalCameraLensFacing(): Flow<Int> =
        appPreferencesFlow.map{
            it.internalCameraLensFacing
        }

    override suspend fun setInternalCameraLensFacing(lensFacing: Int) {
        appPreferences.updateData {
            it.toBuilder()
                .setInternalCameraLensFacing(lensFacing)
                .build()
        }
    }

    override fun getInterenalCameraFlashMode(): Flow<Int> =
        appPreferencesFlow.map{
            it.internalCameraFlashMode
        }

    override suspend fun setInterenalCameraFlashMode(
        @IntRange(from = 0, to = 3) flashMode: Int
    ) {
        appPreferences.updateData {
            it.toBuilder()
                .setInternalCameraFlashMode(flashMode)
                .build()
        }
    }

    override fun getInternalCameraCaptureMode(): Flow<Int> =
        appPreferencesFlow.map {
            it.internalCameraCaptureMode
        }

    override suspend fun setInternalCameraCaptureMode(
        @IntRange(from = 0, to = 2) captureMode: Int
    ) {
        appPreferences.updateData {
            it.toBuilder()
                .setInternalCameraCaptureMode(captureMode)
                .build()
        }
    }


    override suspend fun clearAll() {
        appPreferences.updateData {
            it.toBuilder().clear().build()
        }
    }
}