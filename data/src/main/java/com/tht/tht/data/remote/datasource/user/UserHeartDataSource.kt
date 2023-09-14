package com.tht.tht.data.remote.datasource.user

import com.tht.tht.data.remote.response.user.UserHeartResponse

interface UserHeartDataSource {
    suspend fun sendHeart(
        userUuid: String,
        selectDailyTopicIdx: Int
    ): UserHeartResponse

    suspend fun sendDislike(userUuid: String)
}
