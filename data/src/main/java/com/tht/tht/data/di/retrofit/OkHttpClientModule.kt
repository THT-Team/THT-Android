package com.tht.tht.data.di.retrofit

import com.tht.tht.data.remote.retrofit.TokenRefreshAuthenticator
import com.tht.tht.domain.login.usecase.RefreshFcmTokenLoginUseCase
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object OkHttpClientModule {
    private const val TIMEOUT_MILLIS = 5000L

    @Provides
    fun provideOkHttpClientBuilder(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authorizationInterceptor: Interceptor,
        refreshFcmTokenLoginUseCase: Lazy<RefreshFcmTokenLoginUseCase>
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .authenticator(
                TokenRefreshAuthenticator(
                    refreshFcmTokenLoginUseCase = refreshFcmTokenLoginUseCase
                )
            )
            .addInterceptor(authorizationInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            .build()
    }
}
