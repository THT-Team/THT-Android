package com.tht.tht.data.di.retrofit

import com.tht.tht.data.remote.response.base.BaseResponse
import com.tht.tht.data.remote.response.base.ThtResponse
import com.tht.tht.data.remote.response.user.UserHeartResponse
import com.tht.tht.data.remote.service.THTLoginApi
import com.tht.tht.data.remote.service.THTSignupApi
import com.tht.tht.data.remote.service.dailyusercard.DailyUserCardApiService
import com.tht.tht.data.remote.service.image.ImageService
import com.tht.tht.data.remote.service.image.ImageServiceImpl
import com.tht.tht.data.remote.service.location.RegionCodeApi
import com.tht.tht.data.remote.service.topic.DailyTopicApiService
import com.tht.tht.data.remote.service.user.UserBlockApiService
import com.tht.tht.data.remote.service.user.UserDislikeApiService
import com.tht.tht.data.remote.service.user.UserHeartApiService
import com.tht.tht.data.remote.service.user.UserReportApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.delay
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
    ): UserHeartApiService = object : UserHeartApiService {
        override suspend fun sendHeart(userUuid: String): ThtResponse<UserHeartResponse> {
            delay(500)
            return BaseResponse.Success(
                statusCode = 200,
                response = UserHeartResponse(false)
            )
        }
    }
    // retrofit.create(UserHeartApiService::class.java)

    @Provides
    @Singleton
    fun provideUserDisLikeApiService(
        @ThtAccessTokenRetrofit retrofit: Retrofit
    ): UserDislikeApiService = object : UserDislikeApiService {
        override suspend fun sendDislike(userUuid: String) {
            delay(500)
        }
    }
    // retrofit.create(UserDislikeApiService::class.java)
}
