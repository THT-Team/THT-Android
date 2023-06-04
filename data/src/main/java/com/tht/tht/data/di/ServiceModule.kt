package com.tht.tht.data.di

import com.tht.tht.data.remote.service.LocationService
import com.tht.tht.data.remote.service.LocationServiceImpl
import com.tht.tht.data.remote.service.email.EmailService
import com.tht.tht.data.remote.service.email.EmailServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    @Singleton
    abstract fun bindLocationService(impl: LocationServiceImpl): LocationService

    @Binds
    @Singleton
    abstract fun bindEmailService(impl: EmailServiceImpl): EmailService
}
