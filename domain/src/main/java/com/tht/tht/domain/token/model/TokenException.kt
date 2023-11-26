package com.tht.tht.domain.token.model

import java.io.IOException

sealed class TokenException(
    override val message: String
) : IOException() {

    data class AccessTokenRefreshFailException(
        override val message: String = "Access Token Refresh Request Fail"
    ) : TokenException(message)

    data class AccessTokenExpiredException(
        override val message: String = "Access Token Expired"
    ) : TokenException(message)
}
