package com.tht.tht.data.remote.service.user

import com.tht.tht.data.constant.THTApiConstant
import retrofit2.http.POST
import retrofit2.http.Path

interface UserDislikeApiService {
    @POST(THTApiConstant.User.DisLike)
    suspend fun sendDislike(
        @Path("dont-favorite-user-uuid") userUuid: String,
        @Path("daily-topic-idx") selectDailyTopicIdx: String
    )
}
