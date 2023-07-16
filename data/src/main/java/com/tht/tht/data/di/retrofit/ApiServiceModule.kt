package com.tht.tht.data.di.retrofit

import com.tht.tht.data.remote.service.ImageService
import com.tht.tht.data.remote.service.ImageServiceImpl
import com.tht.tht.data.remote.service.RegionCodeApi
import com.tht.tht.data.remote.service.THTLoginApi
import com.tht.tht.data.remote.service.THTSignupApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun provideTHTSignupApiService(
        @ThtBasicRetrofit retrofit: Retrofit
    ): THTSignupApi {
        return retrofit.create(THTSignupApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTHTLoginApiService(
        @ThtBasicRetrofit retrofit: Retrofit
    ): THTLoginApi {
        return retrofit.create(THTLoginApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRegionCodeApi(
        @RegionCodeRetrofit retrofit: Retrofit
    ): RegionCodeApi {
        return retrofit.create(RegionCodeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideImageService(): ImageService = ImageServiceImpl()
}
