package com.foke.together.external.network

import com.foke.together.external.network.interceptor.BaseUrlInterceptor
import okhttp3.ResponseBody
import javax.inject.Inject

class ExternalCameraDataSource @Inject constructor(
    private val externalCameraApi: ExternalCameraApi,
    private val baseUrlInterceptor: BaseUrlInterceptor
){
    fun setBaseUrl(url: String) {
        baseUrlInterceptor.setBaseUrl(url)
    }

    suspend fun capture(timeout: Int? = null): Result<ResponseBody> {
        return externalCameraApi.capture(timeout)
    }

    fun getPreviewUrl(): String =
        "${baseUrlInterceptor.getBaseUrl()}/preview"
}