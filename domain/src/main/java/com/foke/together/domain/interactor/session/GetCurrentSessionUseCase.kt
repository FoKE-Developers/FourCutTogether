package com.foke.together.domain.interactor.session

import com.foke.together.domain.interactor.entity.SessionData
import com.foke.together.domain.output.SessionRepositoryInterface
import javax.inject.Inject

class GetCurrentSessionUseCase @Inject constructor(
    private val sessionRepository: SessionRepositoryInterface
) {
    operator fun invoke(): SessionData? {
        return sessionRepository.getSession()
    }
}