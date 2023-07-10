package com.tht.tht.data.di

import com.tht.tht.data.repository.DailyTopicRepositoryImpl
import com.tht.tht.data.repository.EmailRepositoryImpl
import com.tht.tht.data.repository.ImageRepositoryImpl
import com.tht.tht.data.repository.LocationRepositoryImpl
import com.tht.tht.data.repository.LoginRepositoryImpl
import com.tht.tht.data.repository.RegionCodeRepositoryImpl
import com.tht.tht.data.repository.SignupRepositoryImpl
import com.tht.tht.data.repository.TokenRepositoryImpl
import com.tht.tht.domain.email.repository.EmailRepository
import com.tht.tht.domain.image.ImageRepository
import com.tht.tht.domain.login.repository.LoginRepository
import com.tht.tht.domain.signup.repository.LocationRepository
import com.tht.tht.domain.signup.repository.RegionCodeRepository
import com.tht.tht.domain.signup.repository.SignupRepository
import com.tht.tht.domain.token.repository.TokenRepository
import com.tht.tht.domain.topic.DailyTopicRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindSignupRepository(impl: SignupRepositoryImpl): SignupRepository

    @Binds
    abstract fun bindLocationRepository(impl: LocationRepositoryImpl): LocationRepository

    @Binds
    abstract fun bindImageRepository(impl: ImageRepositoryImpl): ImageRepository

    @Binds
    abstract fun bindLoginRepository(impl: LoginRepositoryImpl): LoginRepository

    @Binds
    abstract fun bindTokenRepository(impl: TokenRepositoryImpl): TokenRepository

    @Binds
    abstract fun bindRegionCodeRepository(impl: RegionCodeRepositoryImpl): RegionCodeRepository

    @Binds
    abstract fun bindEmailRepository(impl: EmailRepositoryImpl): EmailRepository

    @Binds
    abstract fun bindDailyTopicRepository(impl: DailyTopicRepositoryImpl): DailyTopicRepository
}
