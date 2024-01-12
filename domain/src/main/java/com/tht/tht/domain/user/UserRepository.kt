package com.tht.tht.domain.user

interface UserRepository {
    suspend fun reportUser(userUuid: String, reason: String)

    suspend fun blockUser(userUuid: String)

    suspend fun sendHeart(
        userUuid: String,
        selectDailyTopicIdx: Int
    ): Boolean

    suspend fun sendDislike(
        userUuid: String,
        selectDailyTopicIdx: Int
    )
}
