package com.tht.tht.navigation

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tht.core.navigation.HomeNavigation
import tht.core.navigation.SignupNavigation
import tht.core.navigation.ToHotNavigation
import tht.feature.signin.navigation.SignupNavigationImpl
import tht.feature.tohot.navigation.ToHotNavigationImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {

    @Binds
    abstract fun bindHomeNavigation(impl: HomeNavigationImpl): HomeNavigation

    @Binds
    abstract fun bindSignupNavigation(impl: SignupNavigationImpl): SignupNavigation

    @Binds
    abstract fun bindToHotNavigation(impl: ToHotNavigationImpl): ToHotNavigation
}
