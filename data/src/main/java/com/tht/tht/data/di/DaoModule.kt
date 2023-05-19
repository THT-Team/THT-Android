package com.tht.tht.data.di

import com.tht.tht.data.local.dao.TokenDao
import com.tht.tht.data.local.dao.TokenDaoImpl
import com.tht.tht.data.local.dao.SignupUserDao
import com.tht.tht.data.local.dao.SignupUserDaoImpl
import com.tht.tht.data.local.dao.TermsDao
import com.tht.tht.data.local.dao.TermsDaoImpl
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
    abstract fun bindTermsDao(impl: TermsDaoImpl): TermsDao

    @Binds
    @Singleton
    abstract fun bindSignupUserData(impl: SignupUserDaoImpl): SignupUserDao

    @Binds
    @Singleton
    abstract fun bindTokenDao(impl: TokenDaoImpl): TokenDao
}
