package com.foke.together.domain.interactor


import com.foke.together.domain.input.SetExternalCameraIPInterface
import com.foke.together.domain.interactor.entity.ExternalCameraIP
import com.foke.together.domain.output.AppPreferenceInterface
import javax.inject.Inject

class SetExternalCameraIPUseCase @Inject constructor(
    private val appPreference: AppPreferenceInterface
): SetExternalCameraIPInterface {
    override suspend operator fun invoke(externalCameraIP: ExternalCameraIP)=
        appPreference.setExternalCameraIP(externalCameraIP)
}


