package com.tht.tht.data.di

import com.tht.tht.data.remote.service.LocationService
import com.tht.tht.data.remote.service.LocationServiceImpl
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
    abstract fun provideLocationService(impl: LocationServiceImpl): LocationService
}
