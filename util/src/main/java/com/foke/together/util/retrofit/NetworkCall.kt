package com.foke.together.util.retrofit

import com.foke.together.util.AppLog
import com.foke.together.util.AppPolicy
import okhttp3.Request
import okio.IOException
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkCall<T>(
    private val call: Call<T>,
): Call<Result<T>> {
    override fun enqueue(callback: Callback<Result<T>>) {
        call.enqueue(object: Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (AppPolicy.isDebugMode) {
                    AppLog.e(TAG, "onResponse", "success: $response")
                }

                if (response.isSuccessful) {
                    // success
                    callback.onResponse(
                        this@NetworkCall,
                        Response.success(Result.success(response.body()!!))
                    )
                } else {
                    // error
                    callback.onResponse(
                        this@NetworkCall,
                        Response.success(
                            // TODO: change to custom `error type`
                            Result.failure(Exception())
                        )
                    )
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                if (AppPolicy.isDebugMode) {
                    AppLog.e(TAG, "onResponse", "failure: $t")
                }

                when(t) {
                    // TODO: define each error cases by `error type`
                    is IOException -> Result.failure<T>(t)
                    else -> Result.failure<T>(t)
                }.also {
                    callback.onResponse(this@NetworkCall, Response.success(it))
                }
            }
        })
    }

    override fun clone(): Call<Result<T>> =
        NetworkCall(call.clone())

    override fun execute(): Response<Result<T>> =
        // TODO: check in case of null body
        Response.success(Result.success(call.execute().body()!!))

    override fun isExecuted(): Boolean =
        call.isExecuted

    override fun cancel() =
        call.cancel()

    override fun isCanceled(): Boolean =
        call.isCanceled

    override fun request(): Request =
        call.request()

    override fun timeout(): Timeout =
        call.timeout()

    companion object {
        private val TAG: String = NetworkCall::class.java.simpleName
    }
}