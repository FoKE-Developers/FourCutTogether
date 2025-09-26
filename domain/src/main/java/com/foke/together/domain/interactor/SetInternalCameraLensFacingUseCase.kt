package com.foke.together.domain.interactor

import com.foke.together.domain.output.AppPreferenceInterface
import javax.inject.Inject

class SetInternalCameraLensFacingUseCase @Inject constructor(
    private val appPreferenceRepository: AppPreferenceInterface
) {
    suspend operator fun invoke(lensFacing: Int) = appPreferenceRepository.setInternalCameraLensFacing(lensFacing)
}