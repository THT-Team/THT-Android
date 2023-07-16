package com.tht.tht.data.constant

object THTApiConstant {
    const val TIMEOUT_MILLIS = 5000L

    const val BASE_URL = "http://tht-talk.store/"

    object Signup {
        const val AUTHENTICATION_NUM = "users/join/certification/phone-number"

        const val NICKNAME_DUPLICATE_CHECK = "users/join/nick-name/duplicate-check"

        const val INTERESTS = "ideal-types"
        const val IDEAL_TYPE = "ideal-types"

        const val SIGNUP = "users/join/signup"

        const val SIGNUP_CHECK = "/users/join/exist/user-info"
    }

    object Login {
        const val FCM_TOKEN_LOGIN = "/users/login/normal"
    }
}
