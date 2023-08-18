package com.tht.tht.data.remote.datasource.user

import com.tht.tht.data.remote.mapper.toUnwrap
import com.tht.tht.data.remote.response.user.UserHeartResponse
import com.tht.tht.data.remote.service.user.UserDislikeApiService
import com.tht.tht.data.remote.service.user.UserHeartApiService
import javax.inject.Inject

class UserHeartDataSourceImpl @Inject constructor(
    private val heartApiService: UserHeartApiService,
    private val dislikeApiService: UserDislikeApiService
) : UserHeartDataSource {
    override suspend fun sendHeart(userUuid: String): UserHeartResponse {
        return heartApiService.sendHeart(userUuid).toUnwrap()
    }

    override suspend fun sendDislike(userUuid: String) {
        return dislikeApiService.sendDislike(userUuid)
    }
}
