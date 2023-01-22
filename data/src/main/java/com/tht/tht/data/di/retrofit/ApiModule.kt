package com.tht.tht.data.di.retrofit

import com.tht.tht.data.remote.retrofit.ApiClient
import com.tht.tht.data.remote.service.ThtApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ApiModule {

    @Provides
    @Singleton
    fun provideThtApi(apiClient: ApiClient): ThtApi {
        return apiClient.provideThtApi()
    }
}
