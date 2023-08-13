package com.tht.tht.data.remote.service.user

import com.tht.tht.data.remote.response.base.ThtResponse
import com.tht.tht.data.remote.response.user.UserHeartResponse
import retrofit2.http.Path

interface UserHeartApiService {
    suspend fun sendHeart(
        @Path("heart-user-uuid") userUuid: String
    ): ThtResponse<UserHeartResponse>
}
