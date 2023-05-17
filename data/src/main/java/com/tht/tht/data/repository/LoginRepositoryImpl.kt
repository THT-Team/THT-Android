package com.tht.tht.data.repository

import com.tht.tht.data.remote.datasource.login.LoginDataSource
import com.tht.tht.data.remote.request.login.FcmTokenLoginRequest
import com.tht.tht.domain.login.repository.LoginRepository
import com.tht.tht.domain.token.repository.TokenRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource,
    private val tokenRepository: TokenRepository
) : LoginRepository {

    override suspend fun requestFcmTokenLogin(phone: String) {
        loginDataSource.requestFcmTokenLogin(
            FcmTokenLoginRequest(
                tokenRepository.fetchFcmToken() ?: throw Exception("none token exception"),
                phone
            )
        ).let {
            tokenRepository.updateThtToken(it.accessToken, it.accessTokenExpiresIn)
        }
    }
}
