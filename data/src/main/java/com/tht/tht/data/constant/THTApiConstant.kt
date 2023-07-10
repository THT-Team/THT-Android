package com.tht.tht.data.constant

object THTApiConstant {
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
    object Topic {
        const val DAILY_TOPIC_LIST = "falling/daily-keyword"

        const val SELECT_DAILY_TOPIC = "falling/choice/daily-keyword/{daily-falling-idx}"
    }
    }
}
