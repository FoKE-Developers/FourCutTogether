package com.foke.together.external.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ExternalCameraApi {
    @GET("/capture")
    suspend fun capture(
        @Query("timeout") timeout: Int? = null
    ): Call<ResponseBody>
}