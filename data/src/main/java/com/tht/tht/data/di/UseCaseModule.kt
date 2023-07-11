package com.tht.tht.data.di

import com.tht.tht.domain.dailyusercard.DailyUserCardRepository
import com.tht.tht.domain.dailyusercard.FetchDailyUserCardUseCase
import com.tht.tht.domain.email.repository.EmailRepository
import com.tht.tht.domain.email.usecase.SendInquiryEmailUseCase
import com.tht.tht.domain.image.ImageRepository
import com.tht.tht.domain.image.RemoveImageUrlUseCase
import com.tht.tht.domain.image.UploadImageUseCase
import com.tht.tht.domain.login.repository.LoginRepository
import com.tht.tht.domain.login.usecase.RefreshFcmTokenLoginUseCase
import com.tht.tht.domain.signup.repository.LocationRepository
import com.tht.tht.domain.signup.repository.RegionCodeRepository
import com.tht.tht.domain.signup.repository.SignupRepository
import com.tht.tht.domain.signup.usecase.*
import com.tht.tht.domain.token.repository.TokenRepository
import com.tht.tht.domain.token.token.FetchThtTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideCreateSignupUserUseCase(
        repository: SignupRepository,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): CreateSignupUserUseCase = CreateSignupUserUseCase(
        repository, dispatcher
    )

    @Provides
    fun provideFetchIdealTypeUseCase(
        repository: SignupRepository,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): FetchIdealTypeUseCase = FetchIdealTypeUseCase(
        repository, dispatcher
    )

    @Provides
    fun provideFetchInterestUseCase(
        repository: SignupRepository,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): FetchInterestUseCase = FetchInterestUseCase(
        repository, dispatcher
    )

    @Provides
    fun provideFetchSignupUserUseCase(
        repository: SignupRepository,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): FetchSignupUserUseCase = FetchSignupUserUseCase(
        repository, dispatcher
    )

    @Provides
    fun provideFetchTermsUseCase(
        repository: SignupRepository,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): FetchTermsUseCase = FetchTermsUseCase(
        repository, dispatcher
    )

    @Provides
    fun providePatchSignupDataUseCase(
        repository: SignupRepository,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): PatchSignupDataUseCase = PatchSignupDataUseCase(
        repository, dispatcher
    )

    @Provides
    fun provideRemoveSignupUserUseCase(
        repository: SignupRepository,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): RemoveSignupUserUseCase = RemoveSignupUserUseCase(
        repository, dispatcher
    )

    @Provides
    fun provideRequestAuthenticationUseCase(
        repository: SignupRepository,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): RequestAuthenticationUseCase = RequestAuthenticationUseCase(
        repository, dispatcher
    )

    @Provides
    fun provideRequestSignupUseCase(
        repository: SignupRepository,
        tokenRepository: TokenRepository,
        removeSignupUserUseCase: RemoveSignupUserUseCase,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): RequestSignupUseCase = RequestSignupUseCase(
        repository, tokenRepository, removeSignupUserUseCase, dispatcher
    )

    @Provides
    fun provideCheckSignupStateUseCase(
        repository: SignupRepository,
        tokenRepository: TokenRepository,
        loginRepository: LoginRepository
    ): CheckLoginEnableUseCase = CheckLoginEnableUseCase(
        repository, tokenRepository, loginRepository
    )

    @Provides
    fun provideRequestVerifyUseCase(
        repository: SignupRepository,
        createSignupUserUseCase: CreateSignupUserUseCase,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): RequestPhoneVerifyUseCase = RequestPhoneVerifyUseCase(
        repository, createSignupUserUseCase, dispatcher
    )

    @Provides
    fun provideCheckNicknameDuplicateUseCase(
        repository: SignupRepository,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): CheckNicknameDuplicateUseCase =
        CheckNicknameDuplicateUseCase(repository, dispatcher)

    @Provides
    fun provideUploadImageUseCase(
        repository: ImageRepository
    ): UploadImageUseCase = UploadImageUseCase(repository)

    @Provides
    fun provideRemoveImageUrlUseCase(
        repository: ImageRepository
    ): RemoveImageUrlUseCase = RemoveImageUrlUseCase(repository)

    @Provides
    fun provideFetchCurrentLocationUseCase(
        repository: LocationRepository,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): FetchCurrentLocationUseCase =
        FetchCurrentLocationUseCase(repository, dispatcher)

    @Provides
    fun provideFetchLocationByAddressUseCase(
        repository: LocationRepository,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): FetchLocationByAddressUseCase =
        FetchLocationByAddressUseCase(repository, dispatcher)

    @Provides
    fun provideRequestFcmTokenLoginUseCase(
        tokenRepository: TokenRepository,
        loginRepository: LoginRepository
    ): RefreshFcmTokenLoginUseCase =
        RefreshFcmTokenLoginUseCase(tokenRepository, loginRepository)

    @Provides
    fun provideFetchRegionCodeUseCase(
        repository: RegionCodeRepository,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): FetchRegionCodeUseCase =
        FetchRegionCodeUseCase(repository, dispatcher)

    @Provides
    fun providePatchLocationUseCase(
        useCase: FetchRegionCodeUseCase,
        repository: SignupRepository,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): PatchLocationUseCase =
        PatchLocationUseCase(useCase, repository, dispatcher)

    @Provides
    fun provideSendEmailUseCase(
        repository: EmailRepository
    ): SendInquiryEmailUseCase =
        SendInquiryEmailUseCase(repository)

    @Provides
    fun provideFetchThtTokenUseCase(
        repository: TokenRepository
    ): FetchThtTokenUseCase =
        FetchThtTokenUseCase(repository)
    @Provides
    fun provideFetchDailyUserCardUseCase(
        repository: DailyUserCardRepository
    ): FetchDailyUserCardUseCase =
        FetchDailyUserCardUseCase(repository)
}
