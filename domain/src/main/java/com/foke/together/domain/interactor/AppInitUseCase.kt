package com.foke.together.domain.interactor

import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AppInitUseCase @Inject constructor(
    private val getExternalCameraIPUseCase: GetExternalCameraIPUseCase,
    private val setExternalCameraIPUseCase: SetExternalCameraIPUseCase,
) {
    suspend operator fun invoke() {
        getExternalCameraIPUseCase().firstOrNull()?.run {
            setExternalCameraIPUseCase(this)
        }
    }
}