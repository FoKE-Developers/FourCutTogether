package com.foke.together.data.datasource.remote

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
object WebClientModule {

    @Singleton
    @Provides
    @Named("okHttpWebClient")
    fun provideOkHttpWebClient() = OkHttpClient.Builder()
        .connectTimeout(AppPolicy.WEB_CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(AppPolicy.WEB_READ_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(AppPolicy.WEB_WRITE_TIMEOUT, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    @Named("webClientRetrofit")
    fun provideWebClientRetrofit(
        @Named("okHttpWebClient") okHttpClient: OkHttpClient
    ) = Retrofit.Builder()
        .baseUrl(AppPolicy.WEB_SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create(
            GsonBuilder().setLenient().create()
        ))
        .addCallAdapterFactory(NetworkCallAdapterFactory())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideWebClientApi(
        @Named("webClientRetrofit") retrofit: Retrofit
    ): WebClientApi =
        retrofit.create(WebClientApi::class.java)
}