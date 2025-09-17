package com.foke.together.domain.interactor.entity

data class SessionId (
    val startAt: Long
) {
    override fun toString(): String {
        return startAt.toString()
    }
}