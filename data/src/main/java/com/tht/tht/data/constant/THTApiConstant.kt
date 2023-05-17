package com.tht.tht.data.constant

object THTApiConstant {
    const val BASE_URL = "http://tht-talk.store/"

    object Signup {
        const val AUTHENTICATION_NUM = "users/join/certification/phone-number"

        const val NICKNAME_DUPLICATE_CHECK = "users/join/nick-name/duplicate-check"

        const val INTERESTS = "ideal-types"
        const val IDEAL_TYPE = "ideal-types"

        const val SIGNUP = "users/join/signup"
    }

    object Login {
        const val LOGIN = "/auth/login"
    }
}
