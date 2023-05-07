package com.tht.tht.data.remote.service

import com.tht.tht.data.remote.response.authenticationnumber.AuthenticationNumberResponse
import com.tht.tht.data.remote.response.base.BaseResponse
import com.tht.tht.data.remote.response.base.ThtResponse
import com.tht.tht.data.remote.response.ideal.IdealTypeResponse
import com.tht.tht.data.remote.response.interests.InterestTypeResponse
import com.tht.tht.data.remote.response.nickname.NicknameDuplicateCheckResponse
import com.tht.tht.data.remote.response.signup.SignupResponse
import com.tht.tht.domain.signup.model.SignupUserModel
import kotlinx.coroutines.delay
import retrofit2.Retrofit
import javax.inject.Inject

class MockTHTSignupApi @Inject constructor(
    private val retrofit: Retrofit
) : THTSignupApi {
    override suspend fun requestAuthenticationNumber(phone: String): ThtResponse<AuthenticationNumberResponse> {
        return retrofit.create(THTSignupApi::class.java).requestAuthenticationNumber(phone)
    }

    override suspend fun checkNicknameDuplicate(nickname: String): ThtResponse<NicknameDuplicateCheckResponse> {
        return retrofit.create(THTSignupApi::class.java).checkNicknameDuplicate(nickname)
    }

    override suspend fun fetchIdealType(): ThtResponse<List<IdealTypeResponse>> {
        return retrofit.create(THTSignupApi::class.java).fetchIdealType()
    }

    override suspend fun fetchInterestsType(): ThtResponse<List<InterestTypeResponse>> {
        delay(100)
        val interestType = listOf(InterestTypeResponse("name", "code", 0))
        return BaseResponse.Success(
            statusCode = 200,
            response = interestType
        )
    }

    override suspend fun requestSignup(user: SignupUserModel): ThtResponse<SignupResponse> {
        delay(100)
        return BaseResponse.Success(
            statusCode = 200,
            response = SignupResponse("token", 0L)
        )
    }
}
