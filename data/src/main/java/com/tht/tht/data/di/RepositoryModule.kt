package com.tht.tht.data.di

import com.tht.tht.data.repository.SignupRepositoryImpl
import com.tht.tht.domain.signup.repository.SignupRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindSignupRepository(impl: SignupRepositoryImpl): SignupRepository
}
