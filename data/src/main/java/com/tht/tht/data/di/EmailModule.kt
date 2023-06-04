package com.tht.tht.data.di

import com.tht.tht.data.remote.service.email.EMailSender
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EmailModule {

    @Provides
    @Singleton
    fun provideEmailSender(): EMailSender = EMailSender()
}
