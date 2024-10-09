package com.foke.together.data.datasource.remote

import com.foke.together.data.datasource.remote.dto.AccountRegisterRequest
import com.foke.together.data.datasource.remote.dto.S3PresignedUrlResponse
import com.foke.together.util.AppPolicy
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import java.io.File
import javax.inject.Inject

class WebDataSource @Inject constructor(
    private val webClientApi: WebClientApi,
) {
    private val headers = HashMap<String, String>()

    suspend fun accountWhoAmI() =
        webClientApi.accountWhoAmI(headers)

    suspend fun accountRegister(email: String, name: String, password: String) =
        webClientApi.accountRegister(
            AccountRegisterRequest(email, name, password)
        )

    suspend fun accountSignIn(email: String, password: String): Result<Unit> {
        webClientApi.accountSignin(email, password)
            .onSuccess {
                setToken(it.token)
                return Result.success(Unit)
            }
            .onFailure {
                setToken("")
                return Result.failure(it)
            }
        return Result.failure(Exception("unknown error"))
    }

    suspend fun s3PreSignedUrl(key: String, file: File): Result<S3PresignedUrlResponse> {
        val contentLength = file.length()
        if (contentLength > AppPolicy.WEB_FILE_MAX_CONTENT_LENGTH) {
            return Result.failure(Exception("file size is over limit"))
        }
        return webClientApi.s3PresignedUrl(
            headers = headers,
            key = key,
            contentLength = file.length().toString()
        )
    }

    suspend fun sendFileToCloud(preSignedUrl: String, file: File): Result<ResponseBody> {
        val requestBody = file.asRequestBody("application/octet-stream".toMediaType())
        return webClientApi.sendFileToCloud(
            url = preSignedUrl,
            hashMapOf("Content-Type" to requestBody.contentType().toString()),
            body = requestBody
        )
    }

    private fun setToken(token: String) {
        headers["token"] = token
    }
}