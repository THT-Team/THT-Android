package com.tht.tht.domain.login.repository

import com.tht.tht.domain.login.model.AuthTokenModel
import com.tht.tht.domain.type.SignInType

interface AuthRepository {
    suspend fun loginUser(signInType: SignInType): AuthTokenModel
}
