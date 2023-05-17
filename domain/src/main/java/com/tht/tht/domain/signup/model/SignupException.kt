package com.tht.tht.domain.signup.model

sealed interface SignupException {

    data class SignupUserInvalidateException(
        override val message: String? = "signup user data is invalidate"
    ): SignupException, IllegalArgumentException()

    data class TermsRequireInvalidateException(
        val terms: String,
        override val message: String? = "terms[${terms}] require approve"
    ): SignupException, Exception()

    data class InputDataInvalidateException(
        val data: String,
        override val message: String? = "$data is invalidate"
    ): SignupException, Exception()

    data class InputDataRequireSizeException(
        val data: String,
        val requireSize: Int,
        override val message: String? = "$data require $requireSize"
    ): SignupException, Exception()

    data class SignupUserInfoInvalidateException(
        val data: String,
        override val message: String? = "user info($data) is invalidate"
    ): SignupException, Exception()

}
