package com.foke.together.data.datasource.remote.dto

data class AccountRegisterRequest(
    val email: String,   // max 32
    val name: String,    // max 32
    val password: String // max 32
)