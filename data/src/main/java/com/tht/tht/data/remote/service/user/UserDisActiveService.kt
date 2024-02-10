package com.tht.tht.data.remote.service.user

import com.tht.tht.data.constant.THTApiConstant
import com.tht.tht.data.remote.request.login.UserDisActiveRequest
import com.tht.tht.data.remote.response.base.ThtResponse
import retrofit2.http.Body
import retrofit2.http.HTTP

interface UserDisActiveService {
//    @DELETE(THTApiConstant.Login.USER_DIS_ACTIVE)
    @HTTP(method = "DELETE", path = THTApiConstant.Login.USER_DIS_ACTIVE, hasBody = true)
    suspend fun userDisActive(
        @Body userDisActiveRequest: UserDisActiveRequest
    ): ThtResponse<Unit>
}
