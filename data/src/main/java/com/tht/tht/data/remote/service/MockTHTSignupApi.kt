package com.tht.tht.data.remote.service

import com.tht.tht.data.remote.response.base.BaseResponse
import com.tht.tht.data.remote.response.base.SuccessResponse
import com.tht.tht.data.remote.response.base.ThtResponse
import com.tht.tht.data.remote.response.ideal.IdealTypeResponse
import com.tht.tht.data.remote.response.interests.InterestTypeResponse
import com.tht.tht.data.remote.response.signup.SignupResponse
import com.tht.tht.domain.signup.model.SignupUserModel
import kotlinx.coroutines.delay

class MockTHTSignupApi : THTSignupApi {
    override suspend fun requestAuthenticationNumber(phone: String): ThtResponse<Boolean> {
        delay(100)
        return BaseResponse.Success(
            statusCode = 200,
            response = SuccessResponse(true)
        )
    }

    override suspend fun requestVerify(phone: String, authNumber: String): ThtResponse<Boolean> {
        delay(100)
        val res = when (authNumber.contains('0')) {
            true -> false
            else -> true
        }
        return BaseResponse.Success(
            statusCode = 200,
            response = SuccessResponse(res)
        )
    }

    override suspend fun checkNicknameDuplicate(nickname: String): ThtResponse<Boolean> {
        delay(100)
        val res = when (nickname.lowercase()) {
            "fail", "no", "false" -> false
            "success", "yes", "true" -> true
            else -> throw Throwable("nickname duplicate test exception")
        }
        return BaseResponse.Success(
            statusCode = 200,
            response = SuccessResponse(res)
        )
    }

    override suspend fun fetchIdealType(): ThtResponse<List<IdealTypeResponse>> {
        delay(100)
        val idealTypes = listOf(IdealTypeResponse("name", "code", 0))
        return BaseResponse.Success(
            statusCode = 200,
            response = SuccessResponse(idealTypes)
        )
    }

    override suspend fun fetchInterestsType(): ThtResponse<List<InterestTypeResponse>> {
        delay(100)
        val interestType = listOf(InterestTypeResponse("name", "code", 0))
        return BaseResponse.Success(
            statusCode = 200,
            response = SuccessResponse(interestType)
        )
    }

    override suspend fun requestSignup(user: SignupUserModel): ThtResponse<SignupResponse> {
        delay(100)
        return BaseResponse.Success(
            statusCode = 200,
            response = SuccessResponse(SignupResponse("userId"))
        )
    }
}
