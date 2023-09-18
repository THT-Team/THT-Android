package com.tht.tht.domain.signup.model

sealed interface SignupException {

    data class InvalidateLocationInfo(
        override val message: String? = "location data is invalidate"
    ): SignupException, Exception()

    data class SignupUserInvalidateException(
        override val message: String? = "signup user data is invalidate"
    ): SignupException, IllegalArgumentException()

    data class SignupUserInfoInvalidateException(
        val data: String,
        override val message: String? = "user info($data) is invalidate"
    ): SignupException, Exception()

}
