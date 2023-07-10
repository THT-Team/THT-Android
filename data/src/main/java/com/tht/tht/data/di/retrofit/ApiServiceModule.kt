package com.tht.tht.data.di.retrofit

import com.tht.tht.data.remote.retrofit.ApiClient
import com.tht.tht.data.remote.service.image.ImageService
import com.tht.tht.data.remote.service.image.ImageServiceImpl
import com.tht.tht.data.remote.service.THTLoginApi
import com.tht.tht.data.remote.service.location.RegionCodeApi
import com.tht.tht.data.remote.service.THTSignupApi
import com.tht.tht.data.remote.service.dailyusercard.DailyUserCardApiService
import com.tht.tht.data.remote.service.topic.DailyTopicApiService
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

    @Provides
    @Singleton
    fun provideDailyTopicApiService(
        apiClient: ApiClient
    ): DailyTopicApiService = apiClient.thtApiAdapter.create(DailyTopicApiService::class.java)

    @Provides
    @Singleton
    fun provideDailyUserCardApiService(
        apiClient: ApiClient
    ): DailyUserCardApiService = apiClient.thtApiAdapter.create(DailyUserCardApiService::class.java)
}
