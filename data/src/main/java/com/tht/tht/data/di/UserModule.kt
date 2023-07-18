package com.tht.tht.data.di

import com.tht.tht.data.remote.datasource.user.UserBlockDataSource
import com.tht.tht.data.remote.datasource.user.UserBlockDataSourceImpl
import com.tht.tht.data.remote.datasource.user.UserReportDataSource
import com.tht.tht.data.remote.datasource.user.UserReportDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {

    @Binds
    abstract fun bindUserReportDataSource(impl: UserReportDataSourceImpl): UserReportDataSource

    @Binds
    abstract fun bindUserBlockDataSource(impl: UserBlockDataSourceImpl): UserBlockDataSource

}
