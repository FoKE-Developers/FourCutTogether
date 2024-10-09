package com.foke.together.data.datasource.remote.dto

import com.google.gson.annotations.SerializedName

data class AccountWhoAmIResponse (
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("id") val id: String
)