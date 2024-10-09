package com.foke.together.external.network

import android.graphics.BitmapFactory
import com.foke.together.external.network.interceptor.BaseUrlInterceptor
import com.foke.together.util.AppLog
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ExternalCameraDataSource @Inject constructor(
    private val externalCameraApi: ExternalCameraApi,
    private val baseUrlInterceptor: BaseUrlInterceptor
){
    fun setBaseUrl(url: String) {
        baseUrlInterceptor.setBaseUrl(url)
    }

    suspend fun capture(timeout: Int? = null): Result<ResponseBody> {
        return Result.failure(Exception())
    }

    suspend fun captureTest() {
        externalCameraApi.capture()
            .enqueue(object: Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        AppLog.e(TAG, "capture", "${response.body()}")
                        AppLog.e(TAG, "capture", "${response.body()?.source()}")
                        AppLog.e(TAG, "capture", "${response.body()?.contentType()}")
                        AppLog.e(TAG, "capture", "${response.body()?.contentLength()}")

                        response.body()?.let { body ->
                            val inputStream = body.byteStream()
                            var bitmap = BitmapFactory.decodeStream(inputStream)
                            AppLog.e(TAG, "capture", "${bitmap}")
                            AppLog.e(TAG, "capture", "byteCount: ${bitmap.byteCount}")
                            AppLog.e(TAG, "capture", "width: ${bitmap.width}")
                            AppLog.e(TAG, "capture", "height: ${bitmap.height}")
                        }
                        return
                    }
                    AppLog.e(TAG, "capture", "failed")
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    AppLog.e(TAG, "capture", "error")
                    AppLog.e(TAG, "capture", "$t")

                }
            })
    }


    fun getPreviewUrl(): String =
        "${baseUrlInterceptor.getBaseUrl()}/preview"

    companion object {
        private val TAG: String = ExternalCameraDataSource::class.java.simpleName

    }
}