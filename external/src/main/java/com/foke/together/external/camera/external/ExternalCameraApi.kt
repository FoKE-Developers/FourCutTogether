package com.foke.together.external.camera.external

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface ExternalCameraApi {
    @GET("/capture")
    suspend fun capture(
        @Query("timeout") timeout: Int? = null
    ): Result<ResponseBody>
}