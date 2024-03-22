package com.tht.tht.data.constant

object THTApiConstant {
    const val TIMEOUT_MILLIS = 5000L

    const val BASE_URL = "http://tht-talk.co.kr/"

    object Signup {
        const val AUTHENTICATION_NUM = "users/join/certification/phone-number"

        const val NICKNAME_DUPLICATE_CHECK = "users/join/nick-name/duplicate-check"

        const val INTERESTS = "ideal-types"
        const val IDEAL_TYPE = "ideal-types"

        const val SIGNUP = "users/join/signup"

        const val SIGNUP_CHECK = "users/join/exist/user-info"
    }

    object Login {
        const val FCM_TOKEN_LOGIN = "users/login/normal"
        const val USER_DIS_ACTIVE = "user/account-withdrawal"
    }

    object Topic {
        const val DAILY_TOPIC_LIST = "falling/daily-keyword"

        const val SELECT_DAILY_TOPIC = "falling/choice/daily-keyword/{daily-falling-idx}"
    }

    object Card {
        const val DAILY_USER_CARD = "main/daily-falling/users"
    }

    object User {
        const val ACCESS_TOKEN_REFRESH = "users/login/refresh"

        const val REPORT = "user/report"

        const val BLOCK = "/user/block/{block-user-uuid}"

        const val Heart = "/i-like-you/{favorite-user-uuid}/{daily-topic-idx}"

        const val DisLike = "/i-dont-like-you/{dont-favorite-user-uuid}/{daily-topic-idx}"
    }

    object Chat {
        const val CHAT_LIST = "/chat/rooms"
    }
}
