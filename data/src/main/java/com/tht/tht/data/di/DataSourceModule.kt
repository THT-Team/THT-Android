package com.tht.tht.data.di

import com.tht.tht.data.local.datasource.SignupUserDataSource
import com.tht.tht.data.local.datasource.SignupUserDataSourceImpl
import com.tht.tht.data.local.datasource.TermsDataSource
import com.tht.tht.data.local.datasource.TermsDataSourceImpl
import com.tht.tht.data.remote.datasource.ImageDataSource
import com.tht.tht.data.remote.datasource.ImageDataSourceImpl
import com.tht.tht.data.remote.datasource.SignupApiDataSource
import com.tht.tht.data.remote.datasource.SignupApiDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindImageDataSource(impl: ImageDataSourceImpl): ImageDataSource

    @Binds
    abstract fun bindTermsDataSource(impl: TermsDataSourceImpl): TermsDataSource

    @Binds
    abstract fun bindSignupUserSource(impl: SignupUserDataSourceImpl): SignupUserDataSource

    @Binds
    abstract fun bindSignupApiSource(impl: SignupApiDataSourceImpl): SignupApiDataSource
}
