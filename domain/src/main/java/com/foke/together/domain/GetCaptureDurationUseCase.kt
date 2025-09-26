package com.foke.together.domain

import com.foke.together.domain.output.AppPreferenceInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.time.Duration

class GetCaptureDurationUseCase @Inject constructor(
    private val appPreferenceInterface: AppPreferenceInterface
) {
    operator fun invoke() : Flow<Duration> = appPreferenceInterface.getCaptureDuration()
}