package com.foke.together.domain

import com.foke.together.domain.output.AppPreferenceInterface
import javax.inject.Inject
import kotlin.time.Duration

class SetCaptureDurationUseCase @Inject constructor(
    private val appPreferenceInterface: AppPreferenceInterface
) {
    suspend operator fun invoke(duration: Duration) = appPreferenceInterface.setCaptureDuration(duration)
}