package com.foke.together.domain.output

import com.foke.together.domain.interactor.entity.CutFrame
import com.foke.together.domain.interactor.entity.SessionData
import com.foke.together.domain.interactor.entity.Status

interface SessionRepositoryInterface {
    fun createSession()

    fun getSession(): SessionData?

    fun updateSession(cutFrame: CutFrame)
    fun updateSession(status: Status)
    fun updateSession(cutFrame: CutFrame, status: Status)

    fun clearSession()
}