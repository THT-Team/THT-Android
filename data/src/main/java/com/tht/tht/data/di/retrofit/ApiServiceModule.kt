package com.tht.tht.data.di.retrofit

import com.tht.tht.data.remote.service.THTLoginApi
import com.tht.tht.data.remote.service.THTSignupApi
import com.tht.tht.data.remote.service.chat.ChatService
import com.tht.tht.data.remote.service.dailyusercard.DailyUserCardApiService
import com.tht.tht.data.remote.service.image.ImageService
import com.tht.tht.data.remote.service.image.ImageServiceImpl
import com.tht.tht.data.remote.service.location.RegionCodeApi
import com.tht.tht.data.remote.service.topic.DailyTopicApiService
import com.tht.tht.data.remote.service.user.AccessTokenRefreshService
import com.tht.tht.data.remote.service.user.UserBlockApiService
import com.tht.tht.data.remote.service.user.UserDisActiveService
import com.tht.tht.data.remote.service.user.UserDislikeApiService
import com.tht.tht.data.remote.service.user.UserHeartApiService
import com.tht.tht.data.remote.service.user.UserReportApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
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
    fun provideChatApiService(
        @ThtAccessTokenRetrofit retrofit: Retrofit
    ): ChatService = retrofit.create(ChatService::class.java)

    @Provides
    @Singleton
    fun provideDailyUserCardApiService(
        @ThtAccessTokenRetrofit retrofit: Retrofit
    ): DailyUserCardApiService = retrofit.create(DailyUserCardApiService::class.java)

    @Provides
    @Singleton
    fun provideUserReportApiService(
        @ThtAccessTokenRetrofit retrofit: Retrofit
    ): UserReportApiService = retrofit.create(UserReportApiService::class.java)

    @Provides
    @Singleton
    fun provideUserBlockApiService(
        @ThtAccessTokenRetrofit retrofit: Retrofit
    ): UserBlockApiService = retrofit.create(UserBlockApiService::class.java)

    @Provides
    @Singleton
    fun provideUserHeartApiService(
        @ThtAccessTokenRetrofit retrofit: Retrofit
    ): UserHeartApiService = retrofit.create(UserHeartApiService::class.java)

    @Provides
    @Singleton
    fun provideUserDisLikeApiService(
        @ThtAccessTokenRetrofit retrofit: Retrofit
    ): UserDislikeApiService = retrofit.create(UserDislikeApiService::class.java)

    @Provides
    @Singleton
    fun provideAccessTokenRefreshService(
        @ThtAccessTokenRetrofit retrofit: Retrofit
    ): AccessTokenRefreshService = retrofit.create(AccessTokenRefreshService::class.java)

    @Provides
    @Singleton
    fun provideUserDisActiveService(
        @ThtAccessTokenRetrofit retrofit: Retrofit
    ): UserDisActiveService = retrofit.create(UserDisActiveService::class.java)
}
