package com.tht.tht.data.di

import com.tht.tht.data.datasource.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindTermsDataSource(impl: TermsDataSourceImpl): TermsDataSource

    @Binds
    abstract fun bindSignupUserSource(impl: SignupUserDataSourceImpl): SignupUserDataSource

    @Binds
    abstract fun bindSignupApiSource(impl: SignupApiDataSourceImpl): SignupApiDataSource
}
