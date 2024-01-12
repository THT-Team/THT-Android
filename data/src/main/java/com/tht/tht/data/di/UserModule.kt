package com.tht.tht.data.di

import com.tht.tht.data.remote.datasource.user.AccessTokenRefreshDataSource
import com.tht.tht.data.remote.datasource.user.AccessTokenRefreshDataSourceImpl
import com.tht.tht.data.remote.datasource.user.UserBlockDataSource
import com.tht.tht.data.remote.datasource.user.UserBlockDataSourceImpl
import com.tht.tht.data.remote.datasource.user.UserHeartDataSource
import com.tht.tht.data.remote.datasource.user.UserHeartDataSourceImpl
import com.tht.tht.data.remote.datasource.user.UserReportDataSource
import com.tht.tht.data.remote.datasource.user.UserReportDataSourceImpl
import com.tht.tht.data.repository.UserRepositoryImpl
import com.tht.tht.domain.user.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {

    @Binds
    abstract fun bindAccessTokenRefreshDataSource(
        impl: AccessTokenRefreshDataSourceImpl
    ): AccessTokenRefreshDataSource

    @Binds
    abstract fun bindUserReportDataSource(impl: UserReportDataSourceImpl): UserReportDataSource

    @Binds
    abstract fun bindUserBlockDataSource(impl: UserBlockDataSourceImpl): UserBlockDataSource

    @Binds
    abstract fun bindUserHeartDataSource(impl: UserHeartDataSourceImpl): UserHeartDataSource

    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}
