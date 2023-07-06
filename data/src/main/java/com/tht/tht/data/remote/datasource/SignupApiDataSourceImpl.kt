package com.tht.tht.data.remote.datasource

import com.tht.tht.data.di.IODispatcher
import com.tht.tht.data.remote.mapper.toRemoteRequest
import com.tht.tht.data.remote.mapper.toUnwrap
import com.tht.tht.data.remote.response.authenticationnumber.AuthenticationNumberResponse
import com.tht.tht.data.remote.response.ideal.IdealTypeResponse
import com.tht.tht.data.remote.response.interests.InterestTypeResponse
import com.tht.tht.data.remote.response.signup.SignupCheckResponse
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
    override suspend fun requestAuthenticationNumber(phone: String): AuthenticationNumberResponse {
        return withContext(dispatcher) {
            apiService.requestAuthenticationNumber(phone).toUnwrap { it }
        }
    }

    override suspend fun checkNicknameDuplicate(nickname: String): Boolean {
        return withContext(dispatcher) {
            apiService.checkNicknameDuplicate(nickname).toUnwrap { it.isDuplicate }
        }
    }

    override suspend fun fetchInterests(): List<InterestTypeResponse> {
        return withContext(dispatcher) {
            apiService.fetchInterestsType().toUnwrap { it }
        }
    }

    override suspend fun fetchIdealTypes(): List<IdealTypeResponse> {
        return withContext(dispatcher) {
            apiService.fetchIdealType().toUnwrap { it }
        }
    }

    override suspend fun requestSignup(user: SignupUserModel): SignupResponse {
        return withContext(dispatcher) {
            apiService.requestSignup(user.toRemoteRequest()).toUnwrap { it }
        }
    }

    override suspend fun checkLoginState(phone: String): SignupCheckResponse {
        return withContext(dispatcher) {
            apiService.checkSignupState(phone).toUnwrap { it }
        }
    }
}
