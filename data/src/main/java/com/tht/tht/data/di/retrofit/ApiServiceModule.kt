package com.tht.tht.data.di.retrofit

import com.tht.tht.data.remote.service.THTLoginApi
import com.tht.tht.data.remote.service.THTSignupApi
import com.tht.tht.data.remote.service.dailyusercard.DailyUserCardApiService
import com.tht.tht.data.remote.service.image.ImageService
import com.tht.tht.data.remote.service.image.ImageServiceImpl
import com.tht.tht.data.remote.service.location.RegionCodeApi
import com.tht.tht.data.remote.service.topic.DailyTopicApiService
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

    @Provides
    @Singleton
    fun provideDailyTopicApiService(
        @ThtAccessTokenRetrofit retrofit: Retrofit
    ): DailyTopicApiService = retrofit.create(DailyTopicApiService::class.java)

    @Provides
    @Singleton
    fun provideDailyUserCardApiService(
        @ThtAccessTokenRetrofit retrofit: Retrofit
    ): DailyUserCardApiService = retrofit.create(DailyUserCardApiService::class.java)
}
