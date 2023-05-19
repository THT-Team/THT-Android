package com.tht.tht.data.di

import com.tht.tht.domain.image.ImageRepository
import com.tht.tht.domain.image.RemoveImageUrlUseCase
import com.tht.tht.domain.image.UploadImageUseCase
import com.tht.tht.domain.login.repository.LoginRepository
import com.tht.tht.domain.signup.repository.LocationRepository
import com.tht.tht.domain.signup.repository.SignupRepository
import com.tht.tht.domain.signup.usecase.*
import com.tht.tht.domain.token.repository.TokenRepository
import com.tht.tht.domain.login.usecase.RequestFcmTokenLoginUseCase
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
    fun providePatchSignupBirthdayUseCase(
        repository: SignupRepository,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): PatchSignupBirthdayUseCase = PatchSignupBirthdayUseCase(
        repository, dispatcher
    )

    @Provides
    fun providePatchSignupEmailUseCase(
        repository: SignupRepository,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): PatchSignupEmailUseCase = PatchSignupEmailUseCase(
        repository, dispatcher
    )

    @Provides
    fun providePatchSignupGenderUseCase(
        repository: SignupRepository,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): PatchSignupGenderUseCase = PatchSignupGenderUseCase(
        repository, dispatcher
    )

    @Provides
    fun providePatchSignupIdealTypeUseCase(
        repository: SignupRepository,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): PatchSignupIdealTypeUseCase = PatchSignupIdealTypeUseCase(
        repository, dispatcher
    )

    @Provides
    fun providePatchSignupInterestUseCase(
        repository: SignupRepository,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): PatchSignupInterestUseCase = PatchSignupInterestUseCase(
        repository, dispatcher
    )

    @Provides
    fun providePatchSignupIntroduceUseCase(
        repository: SignupRepository,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): PatchSignupIntroduceUseCase = PatchSignupIntroduceUseCase(
        repository, dispatcher
    )

    @Provides
    fun providePatchSignupLocationUseCase(
        repository: SignupRepository,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): PatchSignupLocationUseCase = PatchSignupLocationUseCase(
        repository, dispatcher
    )

    @Provides
    fun providePatchSignupNickNameUseCase(
        repository: SignupRepository,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): PatchSignupNickNameUseCase = PatchSignupNickNameUseCase(
        repository, dispatcher
    )

    @Provides
    fun providePatchSignupPreferredGenderUseCase(
        repository: SignupRepository,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): PatchSignupPreferredGenderUseCase = PatchSignupPreferredGenderUseCase(
        repository, dispatcher
    )

    @Provides
    fun providePatchSignupProfileImagesUseCase(
        repository: SignupRepository,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): PatchSignupProfileImagesUseCase = PatchSignupProfileImagesUseCase(
        repository, dispatcher
    )

    @Provides
    fun providePatchSignupTermsUseCase(
        repository: SignupRepository,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): PatchSignupTermsUseCase = PatchSignupTermsUseCase(
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
    ) : UploadImageUseCase = UploadImageUseCase(repository)

    @Provides
    fun provideRemoveImageUrlUseCase(
        repository: ImageRepository
    ) : RemoveImageUrlUseCase = RemoveImageUrlUseCase(repository)

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
    ): RequestFcmTokenLoginUseCase =
        RequestFcmTokenLoginUseCase(tokenRepository, loginRepository)
}
