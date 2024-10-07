package com.foke.together.util.retrofit

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class NetworkCallAdapterFactory: CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        // check return type is raw
        if (getRawType(returnType) != Call::class.java) {
            return null
        }

        // check return type is generic
        check(returnType is ParameterizedType) {
            // lazyMessage
            "return type must be parameterized as Call<Result<Foo>> or Call<Result<out Foo>>"
        }

        // get first generic type
        val responseType = getParameterUpperBound(0, returnType)

        // check return type is Result
        if (getRawType(responseType) != Result::class.java) {
            return null
        }

        // check Result has generic type
        check(responseType is ParameterizedType) {
            // lazyMessage
            "Response must be parameterized as Result<Foo> or Result<out Foo>"
        }

        // get generic type from Result
        val successBodyType = getParameterUpperBound(0, responseType)

        // create CallAdapter
        return NetworkCallAdapter<Any>(successBodyType)
    }
}