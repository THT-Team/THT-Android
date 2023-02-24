package tht.feature.signin.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tht.feature.signin.StringProvider
import tht.feature.signin.StringProviderImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class StringProviderModule {

    @Binds
    abstract fun bindStringProvider(impl: StringProviderImpl): StringProvider
}
