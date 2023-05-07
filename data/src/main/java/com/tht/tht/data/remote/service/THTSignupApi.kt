package com.tht.tht.data.remote.service

import com.tht.tht.data.constant.THTApiConstant
import com.tht.tht.data.remote.request.signup.SignupRequest
import com.tht.tht.data.remote.response.authenticationnumber.AuthenticationNumberResponse
import com.tht.tht.data.remote.response.base.ThtResponse
import com.tht.tht.data.remote.response.ideal.IdealTypeResponse
import com.tht.tht.data.remote.response.interests.InterestTypeResponse
import com.tht.tht.data.remote.response.nickname.NicknameDuplicateCheckResponse
import com.tht.tht.data.remote.response.signup.SignupResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface THTSignupApi {

    @GET("${THTApiConstant.Signup.AUTHENTICATION_NUM}/{phone}")
    suspend fun requestAuthenticationNumber(
        @Path("phone")phone: String
    ): ThtResponse<AuthenticationNumberResponse>

    @GET("${THTApiConstant.Signup.NICKNAME_DUPLICATE_CHECK}/{nickname}")
    suspend fun checkNicknameDuplicate(
        @Path("nickname")nickname: String
    ): ThtResponse<NicknameDuplicateCheckResponse>

    @GET(THTApiConstant.Signup.IDEAL_TYPE)
    suspend fun fetchIdealType(): ThtResponse<List<IdealTypeResponse>>

    @GET(THTApiConstant.Signup.INTERESTS)
    suspend fun fetchInterestsType(): ThtResponse<List<InterestTypeResponse>>

    @POST(THTApiConstant.Signup.SIGNUP)
    suspend fun requestSignup(
        @Body body: SignupRequest,
    ): ThtResponse<SignupResponse>
}
