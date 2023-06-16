package com.tht.tht.domain.signup.model

sealed interface SignupException {

    data class SignupUserInvalidateException(
        override val message: String? = "signup user data is invalidate"
    ): SignupException, IllegalArgumentException()

    data class SignupUserInfoInvalidateException(
        val data: String,
        override val message: String? = "user info($data) is invalidate"
    ): SignupException, Exception()

}
