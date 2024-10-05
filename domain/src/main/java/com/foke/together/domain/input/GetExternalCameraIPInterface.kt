package com.foke.together.domain.input

import com.foke.together.domain.interactor.entity.ExternalCameraIP
import kotlinx.coroutines.flow.Flow

interface GetExternalCameraIPInterface {
    operator fun invoke(): Flow<ExternalCameraIP>
}