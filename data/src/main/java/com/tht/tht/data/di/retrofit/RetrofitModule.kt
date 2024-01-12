package com.tht.tht.data.di.retrofit

import com.google.gson.Gson
import com.tht.tht.data.constant.RegionCodeConstant
import com.tht.tht.data.constant.THTApiConstant
import com.tht.tht.data.remote.retrofit.TokenRefreshAuthenticator
import com.tht.tht.data.remote.retrofit.callAdapter.ApiCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @ThtBasicRetrofit
    @Singleton
    fun provideThtBasicTokenRetrofit(
        gson: Gson,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(THTApiConstant.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .connectTimeout(THTApiConstant.TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(ApiCallAdapterFactory())
            .build()
    }

    @Provides
    @ThtAccessTokenRetrofit
    @Singleton
    fun provideThtAccessTokenRetrofit(
        gson: Gson,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authorizationInterceptor: Interceptor,
        tokenRefreshAuthenticator: TokenRefreshAuthenticator
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(THTApiConstant.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .authenticator(tokenRefreshAuthenticator)
                    .addInterceptor(authorizationInterceptor)
                    .addInterceptor(httpLoggingInterceptor)
                    .connectTimeout(THTApiConstant.TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(ApiCallAdapterFactory())
            .build()
    }

    @Provides
    @RegionCodeRetrofit
    @Singleton
    fun provideRegionCodeRetrofit(
        gson: Gson,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(RegionCodeConstant.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .connectTimeout(THTApiConstant.TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(ApiCallAdapterFactory())
            .build()
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ThtBasicRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ThtAccessTokenRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RegionCodeRetrofit
