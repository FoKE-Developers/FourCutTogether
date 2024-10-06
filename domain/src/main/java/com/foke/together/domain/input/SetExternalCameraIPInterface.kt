package com.foke.together.domain.input

import com.foke.together.domain.interactor.entity.ExternalCameraIP

interface SetExternalCameraIPInterface {
    suspend operator fun invoke(externalCameraIP: ExternalCameraIP)
}