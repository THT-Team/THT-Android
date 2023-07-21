package com.tht.tht.data.di

import com.tht.tht.data.local.datasource.SignupUserDataSource
import com.tht.tht.data.local.datasource.SignupUserDataSourceImpl
import com.tht.tht.data.local.datasource.TermsDataSource
import com.tht.tht.data.local.datasource.TermsDataSourceImpl
import com.tht.tht.data.local.datasource.TokenDataSource
import com.tht.tht.data.local.datasource.TokenDataSourceImpl
import com.tht.tht.data.remote.datasource.signup.EmailDataSource
import com.tht.tht.data.remote.datasource.signup.EmailDataSourceImpl
import com.tht.tht.data.remote.datasource.signup.ImageDataSource
import com.tht.tht.data.remote.datasource.signup.ImageDataSourceImpl
import com.tht.tht.data.remote.datasource.signup.LocationDataSource
import com.tht.tht.data.remote.datasource.signup.LocationDataSourceImpl
import com.tht.tht.data.remote.datasource.signup.RegionCodeDataSource
import com.tht.tht.data.remote.datasource.signup.RegionCodeDataSourceImpl
import com.tht.tht.data.remote.datasource.signup.SignupApiDataSource
import com.tht.tht.data.remote.datasource.signup.SignupApiDataSourceImpl
import com.tht.tht.data.remote.datasource.dailyusercard.DailyUserCardDataSource
import com.tht.tht.data.remote.datasource.dailyusercard.DailyUserCardDataSourceImpl
import com.tht.tht.data.remote.datasource.login.LoginDataSource
import com.tht.tht.data.remote.datasource.login.LoginDataSourceImpl
import com.tht.tht.data.remote.datasource.topic.DailyTopicDataSource
import com.tht.tht.data.remote.datasource.topic.DailyTopicDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindTermsDataSource(impl: TermsDataSourceImpl): TermsDataSource

    @Binds
    abstract fun bindSignupUserSource(impl: SignupUserDataSourceImpl): SignupUserDataSource

    @Binds
    abstract fun bindSignupApiSource(impl: SignupApiDataSourceImpl): SignupApiDataSource

    @Binds
    abstract fun bindLocationDataSource(impl: LocationDataSourceImpl): LocationDataSource

    @Binds
    abstract fun bindImageDataSource(impl: ImageDataSourceImpl): ImageDataSource

    @Binds
    abstract fun bindTokenDataSource(impl: TokenDataSourceImpl): TokenDataSource

    @Binds
    abstract fun bindLoginDataSource(impl: LoginDataSourceImpl): LoginDataSource

    @Binds
    abstract fun bindRegionCodeDataSource(impl: RegionCodeDataSourceImpl): RegionCodeDataSource

    @Binds
    abstract fun bindEmailDataSource(impl: EmailDataSourceImpl): EmailDataSource

    @Binds
    abstract fun bindDailyTopicDataSource(impl: DailyTopicDataSourceImpl): DailyTopicDataSource

    @Binds
    abstract fun bindDailyUserCardDataSource(impl: DailyUserCardDataSourceImpl): DailyUserCardDataSource
}
