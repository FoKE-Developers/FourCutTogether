package com.foke.together.util.retrofit

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class NetworkCallAdapter<T>(
    private val responseType: Type
): CallAdapter<T, Call<Result<T>>> {
    override fun responseType(): Type =
        responseType

    override fun adapt(call: Call<T>): Call<Result<T>> =
        // TODO: add NetworkCall for each web-server and external-camera
        NetworkCall(call)
}