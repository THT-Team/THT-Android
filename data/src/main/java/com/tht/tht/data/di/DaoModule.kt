package com.tht.tht.data.di

import com.tht.tht.data.local.dao.TermsDao
import com.tht.tht.data.local.dao.TermsDaoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DaoModule {

    @Binds
    abstract fun bindTermsDao(impl: TermsDaoImpl): TermsDao
}
