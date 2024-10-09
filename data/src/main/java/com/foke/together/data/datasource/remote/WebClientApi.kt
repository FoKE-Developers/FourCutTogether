package com.foke.together.data.datasource.remote

import com.foke.together.data.datasource.remote.dto.AccountRegisterRequest
import com.foke.together.data.datasource.remote.dto.AccountRegisterResponse
import com.foke.together.data.datasource.remote.dto.AccountSigninResponse
import com.foke.together.data.datasource.remote.dto.AccountWhoAmIResponse
import com.foke.together.data.datasource.remote.dto.S3PresignedUrlResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import retrofit2.http.Url

interface WebClientApi {
    // account
    @POST("api/account/register")
    suspend fun accountRegister(
        @Body body: AccountRegisterRequest
    ): Result<AccountRegisterResponse>

    @GET("api/account/signin")
    suspend fun accountSignin(
        @Query("email") email: String,
        @Query("password") password: String
    ): Result<AccountSigninResponse>

    @GET("api/account/who-am-i")
    suspend fun accountWhoAmI(
        @HeaderMap headers: HashMap<String, String>,
    ): Result<AccountWhoAmIResponse>

    // s3
    @GET("api/s3/presigned-url")
    suspend fun s3PresignedUrl(
        @HeaderMap headers: HashMap<String, String>,
        @Query("filename") filename: String,
        @Query("ContentLength") contentLength: String, // max: 20971520
    ): Result<S3PresignedUrlResponse>

    @PUT
    suspend fun sendFileToCloud(
        @Url url: String,
        @HeaderMap headers: HashMap<String, String>,
        @Body body: RequestBody
    ): Result<ResponseBody>
}