package com.tht.tht.data.remote.datasource

import com.tht.tht.data.di.IODispatcher
import com.tht.tht.data.remote.response.base.BaseResponse
import com.tht.tht.data.remote.response.ideal.IdealTypeResponse
import com.tht.tht.data.remote.response.interests.InterestTypeResponse
import com.tht.tht.data.remote.response.signup.SignupResponse
import com.tht.tht.data.remote.service.THTSignupApi
import com.tht.tht.domain.signup.model.SignupUserModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignupApiDataSourceImpl @Inject constructor(
    private val apiService: THTSignupApi,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : SignupApiDataSource {
    override suspend fun requestAuthenticationNumber(phone: String): Boolean {
        return withContext(dispatcher) {
            apiService.requestAuthenticationNumber(phone).let {
                when (it) {
                    is BaseResponse.Success -> true
                    is BaseResponse.SuccessNoBody -> true
                    is BaseResponse.ApiError -> false
                    is BaseResponse.NetworkError -> throw it.exception
                    is BaseResponse.UnknownError -> throw it.throwable
                }
            }
        }
    }

    override suspend fun requestVerify(phone: String, authNumber: String): Boolean {
        return withContext(dispatcher) {
            apiService.requestVerify(phone, authNumber).let {
                when (it) {
                    is BaseResponse.Success -> true
                    is BaseResponse.SuccessNoBody -> true
                    is BaseResponse.ApiError -> false
                    is BaseResponse.NetworkError -> throw it.exception
                    is BaseResponse.UnknownError -> throw it.throwable
                }
            }
        }
    }

    override suspend fun fetchInterests(): List<InterestTypeResponse> {
        return withContext(dispatcher) {
            apiService.fetchInterestsType().let {
                when (it) {
                    is BaseResponse.Success -> it.response.body
                    is BaseResponse.NetworkError -> throw it.exception
                    is BaseResponse.UnknownError -> throw it.throwable
                    else -> throw Exception("Unknown Api Response")
                }
            }
        }
    }

    override suspend fun fetchIdealTypes(): List<IdealTypeResponse> {
        return withContext(dispatcher) {
            apiService.fetchIdealType().let {
                when (it) {
                    is BaseResponse.Success -> it.response.body
                    is BaseResponse.NetworkError -> throw it.exception
                    is BaseResponse.UnknownError -> throw it.throwable
                    else -> throw Exception("Unknown Api Response")
                }
            }
        }
    }

    override suspend fun requestSignup(user: SignupUserModel): SignupResponse {
        return withContext(dispatcher) {
            apiService.requestSignup(user).let {
                when (it) {
                    is BaseResponse.Success -> it.response.body
                    is BaseResponse.NetworkError -> throw it.exception
                    is BaseResponse.UnknownError -> throw it.throwable
                    else -> throw Exception("Unknown Api Response")
                }
            }
        }
    }
}
