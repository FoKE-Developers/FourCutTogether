package com.foke.together.domain.output

import com.foke.together.domain.interactor.entity.CameraSourceType
import kotlinx.coroutines.flow.Flow

interface AppPreferenceInterface {
    fun getCameraSourceType(): Flow<CameraSourceType>
    suspend fun setCameraSourceType(type: CameraSourceType)

    suspend fun clearAll()
}