package com.foke.together.domain.interactor.session

import com.foke.together.domain.output.SessionRepositoryInterface
import javax.inject.Inject

class ClearSessionUseCase @Inject constructor(
    private val sessionRepository: SessionRepositoryInterface
) {
    operator fun invoke() {
        sessionRepository.clearSession()
    }
}