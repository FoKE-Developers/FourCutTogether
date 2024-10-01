package com.foke.together.domain.interactor

import com.foke.together.domain.input.GetCameraSourceTypeInterface
import com.foke.together.domain.output.AppPreferenceInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCameraSourceTypeUseCase @Inject constructor(
    private val appPreference: AppPreferenceInterface
): GetCameraSourceTypeInterface {
    override fun invoke(): Flow<CameraSourceType> =
        appPreference.getCameraSourceType().map { it }
}

enum class CameraSourceType {
    INTERNAL,
    EXTERNAL;

    companion object {
        fun findBy(name: String): CameraSourceType {
            return when (name) {
                INTERNAL.name -> INTERNAL
                EXTERNAL.name -> EXTERNAL
                else -> throw IllegalArgumentException("Unknown value: $name")
            }
        }
    }
}