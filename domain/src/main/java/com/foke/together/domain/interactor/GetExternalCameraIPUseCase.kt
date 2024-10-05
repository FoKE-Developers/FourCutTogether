package com.foke.together.domain.interactor


import com.foke.together.domain.input.GetExternalCameraIPInterface
import com.foke.together.domain.interactor.entity.ExternalCameraIP
import com.foke.together.domain.output.AppPreferenceInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetExternalCameraIPUseCase @Inject constructor(
    private val appPreference: AppPreferenceInterface
): GetExternalCameraIPInterface {
    override fun invoke(): Flow<ExternalCameraIP> =
        appPreference.getExternalCameraIP().map { it }
}


