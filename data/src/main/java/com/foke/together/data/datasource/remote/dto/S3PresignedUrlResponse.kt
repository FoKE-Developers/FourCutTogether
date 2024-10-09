package com.foke.together.data.datasource.remote.dto

import com.google.gson.annotations.SerializedName

data class S3PresignedUrlResponse (
    @SerializedName("presignedUrl") val presignedUrl: String,
)