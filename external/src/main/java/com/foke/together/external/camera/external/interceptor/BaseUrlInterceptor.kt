package com.foke.together.external.camera.external.interceptor

import com.foke.together.util.AppLog
import com.foke.together.util.AppPolicy
import okhttp3.Interceptor
import okhttp3.Response

class BaseUrlInterceptor: Interceptor {
    @Volatile
    private var baseUrl: String? = null
    @Volatile
    private var scheme: String? = null
    @Volatile
    private var host: String? = null
    @Volatile
    private var port: Int? = null

    fun getBaseUrl() = baseUrl ?: AppPolicy.EXTERNAL_CAMERA_DEFAULT_SERVER_URL

    fun setBaseUrl(newBaseUrl: String) {
        baseUrl = newBaseUrl
        if (AppPolicy.isDebugMode) {
            AppLog.i(TAG, "setBaseUrl", "set to: $newBaseUrl")
        }
        try {
            val sep = newBaseUrl.indexOf("://")
            scheme = newBaseUrl.substring(0, sep)
            val newHost = newBaseUrl.substring(sep + 3, newBaseUrl.length)

            val pSep = newHost.lastIndexOf(":")
            port = if (pSep != -1) {
                host = newHost.substring(0, pSep)
                newHost.substring(pSep + 1, newHost.length).toInt()
            } else {
                host = newHost
                null
            }
        } catch (e: Exception) {
            AppLog.e(TAG, "setBaseUrl", "cannot parse newBaseUrl")
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        scheme?.let { s ->
            host?.let { h ->
                val newUrl = request.url.newBuilder().run {
                    scheme(s)
                    host(h)
                    port?.let { port(it) }
                    build()
                }
                request = request.newBuilder()
                    .url(newUrl)
                    .build()
            }
        }
        return chain.proceed(request)
    }

    companion object {
        private val TAG: String = BaseUrlInterceptor::class.java.simpleName
    }
}