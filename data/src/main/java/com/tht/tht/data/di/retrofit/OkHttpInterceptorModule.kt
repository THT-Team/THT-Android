package com.tht.tht.data.di.retrofit

import com.tht.tht.data.BuildConfig
import com.tht.tht.data.remote.retrofit.header.HttpHeaderKey
import com.tht.tht.domain.token.token.FetchThtTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
object OkHttpInterceptorModule {

    @Provides
    fun provideHeaderInterceptor(
        fetchThtTokenUseCase: FetchThtTokenUseCase,
    ): Interceptor = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()
            .header(HttpHeaderKey.CONTENT_TYPE_HEADER_KEY, HttpHeaderKey.CONTENT_TYPE_HEADER_VALUE)
        val accessToken = runBlocking { fetchThtTokenUseCase().getOrNull() }
        if (accessToken != null) {
            requestBuilder.header(
                HttpHeaderKey.AUTHORIZATION_HEADER_KEY,
                "${HttpHeaderKey.BEARER_PREFIX} $accessToken"
            )
        }
        chain.proceed(requestBuilder.build())
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
}
