package com.foke.together.domain.interactor

import com.foke.together.domain.output.AppPreferenceInterface
import com.foke.together.domain.output.ExternalCameraRepositoryInterface
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class InitExternalCameraIPUseCase @Inject constructor(
    private val appPreference: AppPreferenceInterface,
    private val externalCameraRepository: ExternalCameraRepositoryInterface
) {
    suspend operator fun invoke() {
        appPreference.getExternalCameraIP().firstOrNull()?.let { externalCameraIP ->
            externalCameraRepository.setCameraSourceUrl(externalCameraIP.address)
        }
    }
}