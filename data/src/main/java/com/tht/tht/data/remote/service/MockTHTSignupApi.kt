package com.tht.tht.data.remote.service

import com.tht.tht.data.remote.response.authenticationnumber.AuthenticationNumberResponse
import com.tht.tht.data.remote.response.base.BaseResponse
import com.tht.tht.data.remote.response.base.SuccessResponse
import com.tht.tht.data.remote.response.base.ThtResponse
import com.tht.tht.data.remote.response.ideal.IdealTypeResponse
import com.tht.tht.data.remote.response.interests.InterestTypeResponse
import com.tht.tht.data.remote.response.signup.SignupResponse
import com.tht.tht.domain.signup.model.SignupUserModel
import retrofit2.Retrofit
import javax.inject.Inject

class MockTHTSignupApi @Inject constructor(
    private val retrofit: Retrofit
) : THTSignupApi {
    override suspend fun requestAuthenticationNumber(phone: String): ThtResponse<AuthenticationNumberResponse> {
        return retrofit.create(THTSignupApi::class.java).requestAuthenticationNumber(phone)
    }

    override suspend fun fetchIdealType(): ThtResponse<List<IdealTypeResponse>> {
        val idealTypes = listOf(IdealTypeResponse("name", "code", 0))
        return BaseResponse.Success(
            statusCode = 200,
            response = SuccessResponse(idealTypes)
        )
    }

    override suspend fun fetchInterestsType(): ThtResponse<List<InterestTypeResponse>> {
        val interestType = listOf(InterestTypeResponse("name", "code", 0))
        return BaseResponse.Success(
            statusCode = 200,
            response = SuccessResponse(interestType)
        )
    }

    override suspend fun requestSignup(user: SignupUserModel): ThtResponse<SignupResponse> {
        return BaseResponse.Success(
            statusCode = 200,
            response = SuccessResponse(SignupResponse("userId"))
        )
    }
}
