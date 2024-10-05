package com.foke.together.domain.output

import com.foke.together.domain.interactor.entity.CameraSourceType
import com.foke.together.domain.interactor.entity.ExternalCameraIP
import kotlinx.coroutines.flow.Flow

interface AppPreferenceInterface {
    fun getCameraSourceType(): Flow<CameraSourceType>
    suspend fun setCameraSourceType(type: CameraSourceType)

    fun getExternalCameraIP(): Flow<ExternalCameraIP>
    suspend fun setExternalCameraIP(ip: ExternalCameraIP)

    suspend fun clearAll()
}