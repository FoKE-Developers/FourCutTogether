package com.foke.together.domain.input

import com.foke.together.domain.interactor.CameraSourceType
import kotlinx.coroutines.flow.Flow

interface GetCameraSourceTypeInterface {
    operator fun invoke(): Flow<CameraSourceType>
}