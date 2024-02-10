package com.tht.tht.data.di.retrofit

import com.google.gson.Gson
import com.tht.tht.data.BuildConfig
import com.tht.tht.data.remote.retrofit.ThtHeaderInterceptor
import com.tht.tht.data.remote.retrofit.TokenRefreshAuthenticator
import com.tht.tht.domain.token.token.FetchThtAccessTokenUseCase
import com.tht.tht.domain.token.token.RefreshThtAccessTokenUseCase
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
object OkHttpInterceptorModule {

    @Provides
    fun provideHeaderInterceptor(
        gson: Gson,
        fetchThtAccessTokenUseCase: Lazy<FetchThtAccessTokenUseCase>
    ): Interceptor = ThtHeaderInterceptor(gson, fetchThtAccessTokenUseCase)

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
