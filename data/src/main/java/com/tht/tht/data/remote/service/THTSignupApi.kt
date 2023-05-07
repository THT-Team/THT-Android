package com.tht.tht.data.remote.service

import com.google.gson.annotations.SerializedName
import com.tht.tht.data.constant.THTApiConstant
import com.tht.tht.data.remote.request.signup.SignupRequest
import com.tht.tht.data.remote.response.authenticationnumber.AuthenticationNumberResponse
import com.tht.tht.data.remote.response.base.ThtResponse
import com.tht.tht.data.remote.response.ideal.IdealTypeResponse
import com.tht.tht.data.remote.response.interests.InterestTypeResponse
import com.tht.tht.data.remote.response.nickname.NicknameDuplicateCheckResponse
import com.tht.tht.data.remote.response.signup.SignupResponse
import com.tht.tht.domain.signup.model.SignupUserModel
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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
        user: SignupRequest,
        agreement: SignupRequest.Agreement = user.agreement,
        birthDay: String = user.birthDay,
        deviceKey: String = user.deviceKey,
        email: String = user.email,
        gender: String = user.gender,
        idealTypeList: List<Long> = user.idealTypeList,
        interestList: List<Long> = user.interestList,
        introduction: String = user.introduction,
        locationRequest: SignupRequest.LocationRequest = user.locationRequest,
        phoneNumber: String = user.phoneNumber,
        photoList: List<String> = user.photoList,
        preferGender: String = user.preferGender,
        username: String = user.username
    ): ThtResponse<SignupResponse>
}
