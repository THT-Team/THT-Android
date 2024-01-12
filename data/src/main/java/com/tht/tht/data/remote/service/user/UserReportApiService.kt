package com.tht.tht.data.remote.service.user

import com.tht.tht.data.constant.THTApiConstant
import com.tht.tht.data.remote.request.user.UserReportRequest
import com.tht.tht.data.remote.response.base.ThtResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserReportApiService {
    @POST(THTApiConstant.User.REPORT)
    suspend fun reportUser(
        @Body body: UserReportRequest
    ): ThtResponse<Unit>
}
