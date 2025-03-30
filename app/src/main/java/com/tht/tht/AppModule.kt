package com.tht.tht

import android.app.Application
import com.tht.tht.navigation.BottomNavigationProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tht.core.navigation.BottomNavigationProvider

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @ApplicationContext
    @Provides
    fun provideApplicationContext(application: Application) = application

    @Provides
    fun provideApplication(): BottomNavigationProvider = BottomNavigationProviderImpl()
}
