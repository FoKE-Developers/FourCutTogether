package com.foke.together.external.repository

import com.foke.together.domain.output.SessionRepositoryInterface
import com.foke.together.util.TimeUtil
import javax.inject.Inject

class SessionRepository @Inject constructor(): SessionRepositoryInterface {
    private var sessionKey: String = ""
    override suspend fun setSessionKey() {
        sessionKey = TimeUtil.getCurrentTimeSec()
    }

    override fun getSessionKey(): String {
        return sessionKey
    }
}