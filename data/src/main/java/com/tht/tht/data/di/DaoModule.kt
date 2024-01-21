package com.tht.tht.data.di

import com.tht.tht.data.local.dao.SignupUserDao
import com.tht.tht.data.local.dao.SignupUserDaoImpl
import com.tht.tht.data.local.dao.TokenDao
import com.tht.tht.data.local.dao.TokenDaoImpl
import com.tht.tht.data.local.dao.topic.DailyTopicDao
import com.tht.tht.data.local.dao.topic.DailyTopicDaoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DaoModule {

    @Binds
    @Singleton
    abstract fun bindSignupUserData(impl: SignupUserDaoImpl): SignupUserDao

    @Binds
    @Singleton
    abstract fun bindTokenDao(impl: TokenDaoImpl): TokenDao

    @Binds
    @Singleton
    abstract fun bindDailyTopicDao(impl: DailyTopicDaoImpl): DailyTopicDao
}
