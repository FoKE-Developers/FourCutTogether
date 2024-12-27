package com.foke.together.domain.interactor.entity

data class SessionData (
    val sessionId: SessionId? = null,
    val cutFrame: CutFrame,
    val status: Status
)

enum class Status {
    INIT,
    SELECT_FRAME,
    CAPTURE,
    GENERATE_PHOTO,
    SHARE
}