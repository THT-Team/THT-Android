package com.tht.tht.data.remote.mapper.login

import com.tht.tht.data.remote.response.login.LoginResponse
import com.tht.tht.domain.login.model.AuthTokenModel

fun LoginResponse.toAuthTokenModel() = AuthTokenModel(
    accessToken = accessToken,
    refreshToken = refreshToken
)
