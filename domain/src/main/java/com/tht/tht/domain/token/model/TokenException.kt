package com.tht.tht.domain.token.model

import java.io.IOException

interface NeedLogoutException

sealed class TokenException(
    override val message: String
) : IOException() {

    data class AccessTokenRefreshFailException(
        override val message: String = "Access Token Refresh Request Fail"
    ) : TokenException(message), NeedLogoutException

    data class AccessTokenExpiredException(
        override val message: String = "Access Token Expired"
    ) : TokenException(message)

    data class RefreshTokenExpiredException(
        override val message: String = "Refresh Token Expired"
    ) : TokenException(message), NeedLogoutException
}
