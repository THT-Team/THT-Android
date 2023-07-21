package com.tht.tht.data.remote.service.user

import com.tht.tht.data.constant.THTApiConstant
import com.tht.tht.data.remote.response.base.ThtResponse
import retrofit2.http.POST
import retrofit2.http.Path

interface UserBlockApiService {
    @POST(THTApiConstant.User.BLOCK)
    suspend fun blockUser(
        @Path("block-user-uuid") userUuid: String
    ): ThtResponse<Unit>
}
