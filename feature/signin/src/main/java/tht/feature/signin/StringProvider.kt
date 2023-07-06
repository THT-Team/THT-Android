package tht.feature.signin

interface StringProvider {

    fun getString(id: ResId): String

    fun getString(id: ResId, vararg formatArg: Any): String

    enum class ResId {
        Loading,
        CustomerService,
        InvalidatePhone,
        InvalidateSignupProcess,
        SendAuthSuccess,
        SendAuthFail,
        CheckSignupStateFail,
        AuthTimeout,
        ResendAuthSuccess,
        VerifyFail,
        EmailPatchFail,
        TermsFetchFail,
        TermsPatchFail,
        RequireTermsNeedSelect,
        NickNamePatchFail,
        DuplicateCheckLoading,
        DuplicateNickname,
        DuplicateCheckFail,
        PreferredGenderPatchFail,
        IdealFetchFail,
        IdealPatchFail,
        InterestFetchFail,
        InterestPatchFail,
        ProfileImagePartialUploadFail,
        ProfileImageUploadFail,
        ProfileImagePatchFail,
        BirthdayPatchFail,
        InvalidateLocation,
        FetchLocationFail,
        AutoLocationFetchFail,
        LocationPatchFail,
        RegionCodeFetchFail,
        SignupFail,
        InvalidDate,
        EmailSendFail
    }
}
