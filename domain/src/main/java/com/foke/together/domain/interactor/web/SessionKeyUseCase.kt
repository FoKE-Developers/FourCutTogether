package com.foke.together.domain.interactor.web

import com.foke.together.domain.output.SessionRepositoryInterface
import javax.inject.Inject

class SessionKeyUseCase @Inject constructor(
  private val sessionRepository: SessionRepositoryInterface
) {
    suspend fun setSessionKey() {
        sessionRepository.setSessionKey()
    }

    fun getSessionKey(): String {
        return sessionRepository.getSessionKey()
    }
}