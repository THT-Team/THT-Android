package com.tht.tht.data.di.retrofit

import com.tht.tht.data.local.dao.TokenDao
import com.tht.tht.data.remote.retrofit.TokenRefreshAuthenticator
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
        tokenDao: TokenDao,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .authenticator(TokenRefreshAuthenticator(tokenDao))
            .addInterceptor(authorizationInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            .build()
    }
}
