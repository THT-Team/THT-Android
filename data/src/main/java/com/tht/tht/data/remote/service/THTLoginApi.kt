package com.tht.tht.data.remote.service

import com.tht.tht.data.constant.THTApiConstant
import com.tht.tht.data.remote.request.login.LoginRequest
import com.tht.tht.data.remote.response.base.ThtResponse
import com.tht.tht.data.remote.response.ideal.IdealTypeResponse
import com.tht.tht.data.remote.response.interests.InterestTypeResponse
import com.tht.tht.data.remote.response.login.LoginResponse
import com.tht.tht.data.remote.response.signup.SignupResponse
import com.tht.tht.domain.signup.model.SignupUserModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface THTLoginApi {

    @POST(THTApiConstant.Login.LOGIN)
    suspend fun requestLogin(
        @Body loginRequest: LoginRequest
    ): ThtResponse<LoginResponse>
}
