package tht.feature.signin

interface StringProvider {

    fun getString(id: ResId): String

    fun getString(id: ResId, vararg formatArg: Any): String

    enum class ResId {
        CustomerService,
        InvalidatePhone,
        InvalidateSignupProcess,
        SendAuthSuccess,
        SendAuthFail,
        AuthTimeout,
        ResendAuthSuccess,
        VerifyFail,
        EmailPatchFail,
        TermsFetchError,
        RequireTermsNeedSelect,
        NickNamePatchFail,
        DuplicateCheckLoading,
        DuplicateNickname,
        DuplicateCheckFail,
        IdealFetchFail,
        IdealPatchFail
    }
}
