package com.foke.together.data.repository

import androidx.datastore.core.DataStore
import com.foke.together.AppPreferences
import com.foke.together.CameraSource
import com.foke.together.CutFrameSource
import com.foke.together.domain.interactor.entity.CameraSourceType
import com.foke.together.domain.interactor.entity.CutFrameSourceType
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

    override suspend fun clearAll() {
        appPreferences.updateData {
            it.toBuilder().clear().build()
        }
    }

    override fun getCutFrameSourceType(): Flow<CutFrameSourceType> =
        appPreferencesFlow.map {
            it.cutFrameSource?.run {
                when (this) {
                    CutFrameSource.MAKER_FAIRE -> CutFrameSourceType.MAKER_FAIRE
                    CutFrameSource.FOKE_LIGHT -> CutFrameSourceType.FOKE_LIGHT
                    CutFrameSource.FOKE_DARK -> CutFrameSourceType.FOKE_DARK
                    CutFrameSource.UNRECOGNIZED -> null
                }
            } ?: CutFrameSourceType.MAKER_FAIRE // set to default INTERNAL
        }

    override suspend fun setCutFrameSourceType(type: CutFrameSourceType){
        when (type) {
            CutFrameSourceType.MAKER_FAIRE -> CutFrameSource.MAKER_FAIRE
            CutFrameSourceType.FOKE_LIGHT -> CutFrameSource.FOKE_LIGHT
            CutFrameSourceType.FOKE_DARK -> CutFrameSource.FOKE_DARK
        }.apply {
            appPreferences.updateData {
                it.toBuilder()
                    .setCutFrameSource(this)
                    .build()
            }
        }
    }
}