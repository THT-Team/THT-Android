package com.tht.tht.data.remote.service.user

import com.tht.tht.data.constant.THTApiConstant
import com.tht.tht.data.remote.response.base.ThtResponse
import com.tht.tht.data.remote.response.user.UserHeartResponse
import retrofit2.http.POST
import retrofit2.http.Path

interface UserHeartApiService {
    @POST(THTApiConstant.User.Heart)
    suspend fun sendHeart(
        @Path("favorite-user-uuid") userUuid: String,
        @Path("daily-topic-idx") selectDailyTopicIdx: String
    ): ThtResponse<UserHeartResponse>
}
