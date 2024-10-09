package com.foke.together.data.datasource.remote.dto

import com.google.gson.annotations.SerializedName

data class AccountSigninResponse (
    @SerializedName("token") val token: String
)