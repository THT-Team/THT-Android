package com.tht.tht.data.remote.service

import com.tht.tht.data.remote.request.signup.SignupRequest
import com.tht.tht.data.remote.response.authenticationnumber.AuthenticationNumberResponse
import com.tht.tht.data.remote.response.base.BaseResponse
import com.tht.tht.data.remote.response.base.ThtResponse
import com.tht.tht.data.remote.response.ideal.IdealTypeResponse
import com.tht.tht.data.remote.response.interests.InterestTypeResponse
import com.tht.tht.data.remote.response.nickname.NicknameDuplicateCheckResponse
import com.tht.tht.data.remote.response.signup.SignupResponse
import kotlinx.coroutines.delay
import retrofit2.Retrofit
import retrofit2.create
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
        return retrofit.create(THTSignupApi::class.java).fetchInterestsType()
    }

    override suspend fun requestSignup(
        body: SignupRequest
    ): ThtResponse<SignupResponse> {
        return retrofit.create(THTSignupApi::class.java).requestSignup(body)
    }
}
