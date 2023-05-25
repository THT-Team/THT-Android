package com.tht.tht.data.di.retrofit

import com.tht.tht.data.remote.retrofit.ApiClient
import com.tht.tht.data.remote.service.ImageService
import com.tht.tht.data.remote.service.ImageServiceImpl
import com.tht.tht.data.remote.service.THTLoginApi
import com.tht.tht.data.remote.service.RegionCodeApi
import com.tht.tht.data.remote.service.THTSignupApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun provideTHTSignupApiService(
        apiClient: ApiClient
    ): THTSignupApi {
        return apiClient.provideTHTSignupApi()
    }

    @Provides
    @Singleton
    fun provideTHTLoginApiService(
        apiClient: ApiClient
    ): THTLoginApi {
        return apiClient.provideTHTLoginApi()
    }

    @Provides
    @Singleton
    fun provideRegionCodeApi(
        apiClient: ApiClient
    ): RegionCodeApi {
        return apiClient.provideRegionCodeApi()
    }

    @Provides
    @Singleton
    fun provideImageService(): ImageService = ImageServiceImpl()
}
