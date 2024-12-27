package com.foke.together.external.repository

import com.foke.together.domain.interactor.entity.CutFrame
import com.foke.together.domain.interactor.entity.DefaultCutFrameSet
import com.foke.together.domain.interactor.entity.SessionData
import com.foke.together.domain.interactor.entity.SessionId
import com.foke.together.domain.interactor.entity.Status
import com.foke.together.domain.output.SessionRepositoryInterface
import com.foke.together.util.AppLog
import com.foke.together.util.TimeUtil
import javax.inject.Inject

class SessionRepository @Inject constructor(): SessionRepositoryInterface {

    private var sessionData: SessionData? = null

    override fun createSession() {
        sessionData = SessionData(
            SessionId(startAt = TimeUtil.getCurrentTimestamp()),
            cutFrame = DefaultCutFrameSet.None,
            status = Status.INIT
        )
        AppLog.i(TAG, "createSession", "sessionData: $sessionData")

        // TODO: save session data to Pref.
    }

    override fun getSession(): SessionData? {
        AppLog.i(TAG, "getSession", "sessionData: $sessionData")

        return sessionData
        // TODO: read session data to Pref.
    }

    override fun updateSession(cutFrame: CutFrame) {
        sessionData = sessionData?.let {
            SessionData(it.sessionId, cutFrame, it.status)
        }
        AppLog.i(TAG, "updateSession", "sessionData: $sessionData")

        // TODO: save session data to Pref.
    }

    override fun updateSession(status: Status) {
        sessionData = sessionData?.let {
            SessionData(it.sessionId, it.cutFrame, status)
        }
        AppLog.i(TAG, "updateSession", "sessionData: $sessionData")

        // TODO: save session data to Pref.
    }

    override fun updateSession(cutFrame: CutFrame, status: Status) {
        sessionData = sessionData?.let {
            SessionData(it.sessionId, cutFrame, status)
        }
        AppLog.i(TAG, "updateSession", "sessionData: $sessionData")

        // TODO: save session data to Pref.
    }

    override fun clearSession() {
        sessionData = null
        AppLog.i(TAG, "clearSession", "sessionData: $sessionData")

        // TODO: clear session data to Pref.
    }

    companion object {
        private val TAG = SessionRepository::class.java.simpleName
    }
}