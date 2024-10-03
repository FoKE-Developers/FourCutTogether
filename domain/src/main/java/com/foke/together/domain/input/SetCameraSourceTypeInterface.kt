package com.foke.together.domain.input

import com.foke.together.domain.interactor.entity.CameraSourceType

interface SetCameraSourceTypeInterface {
    suspend operator fun invoke(cameraSourceType: CameraSourceType)
}