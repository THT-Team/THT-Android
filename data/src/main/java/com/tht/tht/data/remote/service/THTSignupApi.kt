package com.tht.tht.data.remote.service

import com.tht.tht.data.constant.THTApiConstant
import com.tht.tht.data.remote.response.authenticationnumber.AuthenticationNumberResponse
import com.tht.tht.data.remote.response.base.ThtResponse
import com.tht.tht.data.remote.response.ideal.IdealTypeResponse
import com.tht.tht.data.remote.response.interests.InterestTypeResponse
import com.tht.tht.data.remote.response.signup.SignupResponse
import com.tht.tht.domain.signup.model.SignupUserModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface THTSignupApi {

    @GET("${THTApiConstant.Signup.AUTHENTICATION_NUM}/{phone}")
    suspend fun requestAuthenticationNumber(
        @Path("phone")phone: String
    ): ThtResponse<AuthenticationNumberResponse>

    @GET(THTApiConstant.Signup.NICKNAME_DUPLICATE_CHECK)
    suspend fun checkNicknameDuplicate(
        @Query("nickname")nickname: String
    ): ThtResponse<Boolean>

    @GET(THTApiConstant.Signup.IDEAL_TYPE)
    suspend fun fetchIdealType(): ThtResponse<List<IdealTypeResponse>>

    @GET(THTApiConstant.Signup.INTERESTS)
    suspend fun fetchInterestsType(): ThtResponse<List<InterestTypeResponse>>

    @GET(THTApiConstant.Signup.SIGNUP)
    suspend fun requestSignup(
        @Query("user")user: SignupUserModel
    ): ThtResponse<SignupResponse>
}
