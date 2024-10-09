package com.foke.together.domain.interactor.entity

data class PreSignedFileData (
    val key: String,
    val contentLength: Long,
    val preSignedUrl: String,
)