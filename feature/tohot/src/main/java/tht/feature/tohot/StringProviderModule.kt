package tht.feature.tohot

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StringProviderModule {

    @Binds
    abstract fun bindStringProvider(impl: StringProviderImpl): StringProvider
}
