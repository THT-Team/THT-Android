package com.tht.tht.data.di.retrofit

import com.tht.tht.data.BuildConfig
import com.tht.tht.data.remote.retrofit.TokenRefreshAuthenticator
import com.tht.tht.data.remote.retrofit.header.HttpHeaderKey
import com.tht.tht.domain.token.model.TokenException
import com.tht.tht.domain.token.token.FetchThtAccessTokenUseCase
import com.tht.tht.domain.token.token.RefreshThtAccessTokenUseCase
import dagger.Lazy
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
        fetchThtAccessTokenUseCase: Lazy<FetchThtAccessTokenUseCase>
    ): Interceptor = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()
            .header(HttpHeaderKey.CONTENT_TYPE_HEADER_KEY, HttpHeaderKey.CONTENT_TYPE_HEADER_VALUE)
        val accessToken = runBlocking { fetchThtAccessTokenUseCase.get().invoke().getOrNull() }
        if (accessToken != null) {
            requestBuilder.header(
                HttpHeaderKey.AUTHORIZATION_HEADER_KEY,
                "${HttpHeaderKey.BEARER_PREFIX} $accessToken"
            )
        }
        chain.proceed(requestBuilder.build()).also {  response ->
            when (response.code) {
                500 -> throw TokenException.RefreshTokenExpiredException()
            }
        }
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

    @Provides
    fun provideTokenRefreshAuthenticator(
        refreshThtAccessTokenUseCase: Lazy<RefreshThtAccessTokenUseCase>
    ): TokenRefreshAuthenticator = TokenRefreshAuthenticator(refreshThtAccessTokenUseCase)
}
