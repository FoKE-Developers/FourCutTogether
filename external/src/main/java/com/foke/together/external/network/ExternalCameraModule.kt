package com.foke.together.external.network

import com.foke.together.external.network.interceptor.BaseUrlInterceptor
import com.foke.together.util.AppPolicy
import com.foke.together.util.retrofit.NetworkCallAdapterFactory
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExternalCameraModule {

    @Provides
    @Named("cameraIPUrl")
    // TODO: need to check in phase3; is it valid injection?
    fun provideCameraIPUrl(): String = AppPolicy.EXTERNAL_CAMERA_DEFAULT_SERVER_URL

    @Provides
    @Singleton
    fun provideBaseUrlInterceptor() = BaseUrlInterceptor()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        baseUrlInterceptor: BaseUrlInterceptor
    ) = OkHttpClient.Builder()
        .connectTimeout(AppPolicy.EXTERNAL_CAMERA_CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(AppPolicy.EXTERNAL_CAMERA_READ_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(AppPolicy.EXTERNAL_CAMERA_WRITE_TIMEOUT, TimeUnit.SECONDS)
        // .addInterceptor(MockInterceptor()) // TODO: add mock code for test
        .addInterceptor(baseUrlInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideExternalCameraServerRetrofit(
        okHttpClient: OkHttpClient,
        @Named("cameraIPUrl") cameraIPUrl: String
    ) = Retrofit.Builder()
        .baseUrl(cameraIPUrl)
        .addConverterFactory(GsonConverterFactory.create(
            GsonBuilder().setLenient().create()
        ))
        .addCallAdapterFactory(NetworkCallAdapterFactory())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ExternalCameraApi =
        retrofit.create(ExternalCameraApi::class.java)
}