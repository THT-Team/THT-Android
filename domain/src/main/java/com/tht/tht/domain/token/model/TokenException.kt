package com.tht.tht.domain.token.model

import com.tht.tht.domain.signup.model.SignupException

sealed interface TokenException {

    data class NoneTokenException(
        override val message: String? = "none token exception"
    ): TokenException, Exception()
}
