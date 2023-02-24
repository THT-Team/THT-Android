package tht.feature.signin

interface StringProvider {

    fun getString(id: ResId): String

    fun getString(id: ResId, vararg formatArg: Any): String

    enum class ResId {
        InvalidatePhone,
        SendAuthSuccess,
        SendAuthFail,
        AuthTimeout,
        ResendAuthSuccess,
        VerifyFail
    }

}
