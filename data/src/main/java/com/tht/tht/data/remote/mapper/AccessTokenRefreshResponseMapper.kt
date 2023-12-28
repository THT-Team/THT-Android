package com.tht.tht.data.remote.mapper

import com.tht.tht.data.remote.response.user.AccessTokenRefreshResponse
import com.tht.tht.domain.token.model.AccessTokenModel

fun AccessTokenRefreshResponse.toAccessTokenModel(): AccessTokenModel {
    return AccessTokenModel(
        accessToken = accessToken,
        expiredTime = accessTokenExpiresIn
    )
}
