package com.tht.tht.data.di

import com.tht.tht.data.local.service.TermsService
import com.tht.tht.data.local.service.TermsServiceImpl
import com.tht.tht.data.remote.service.email.EmailService
import com.tht.tht.data.remote.service.email.EmailServiceImpl
import com.tht.tht.data.remote.service.location.LocationService
import com.tht.tht.data.remote.service.location.LocationServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    abstract fun bindLocationService(impl: LocationServiceImpl): LocationService

    @Binds
    abstract fun bindEmailService(impl: EmailServiceImpl): EmailService

    @Binds
    abstract fun bindTermsService(impl: TermsServiceImpl): TermsService

}
