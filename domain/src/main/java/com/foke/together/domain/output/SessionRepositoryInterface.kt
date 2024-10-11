package com.foke.together.domain.output

interface SessionRepositoryInterface {
    suspend fun setSessionKey()
    fun getSessionKey(): String
}