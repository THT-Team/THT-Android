package com.tht.tht.data.di.retrofit

import androidx.viewbinding.BuildConfig
import com.tht.tht.data.remote.retrofit.header.HttpHeaderKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
class OkHttpInterceptor {

    // TODO Preference 추가해서 accesToken 연결작업하기
    @Provides
    fun provideHeaderInterceptor(): Interceptor = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()
            .header(HttpHeaderKey.CONTENT_TYPE_HEADER_KEY, HttpHeaderKey.CONTENT_TYPE_HEADER_VALUE)
        val accessToken = ""
        if (accessToken.isNotEmpty()) {
            requestBuilder.header(
                HttpHeaderKey.AUTHORIZATION_HEADER_KEY,
                "${HttpHeaderKey.BEARER_PREFIX} $accessToken"
            )
        }
        chain.proceed(requestBuilder.build())
    }

    @Provides
    fun provideHttpLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
}
