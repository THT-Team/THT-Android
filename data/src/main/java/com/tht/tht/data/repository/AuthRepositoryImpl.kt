package com.tht.tht.data.repository

import com.tht.tht.data.di.DefaultDispatcher
import com.tht.tht.data.remote.datasource.login.LoginDataSource
import com.tht.tht.data.remote.request.login.LoginRequest
import com.tht.tht.domain.login.model.AuthTokenModel
import com.tht.tht.domain.login.repository.AuthRepository
import com.tht.tht.domain.type.SignInType
import kotlinx.coroutines.CoroutineDispatcher

class AuthRepositoryImpl(
    private val loginDataSource: LoginDataSource,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : AuthRepository {
    override suspend fun loginUser(signInType: SignInType): AuthTokenModel {
        return loginDataSource.requestLogin(LoginRequest(socialType = signInType.name))
    }
}
