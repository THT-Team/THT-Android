package com.tht.tht.data.remote.mapper

import com.tht.tht.data.remote.response.login.FcmTokenLoginResponse
import com.tht.tht.domain.token.model.FcmTokenLoginResponseModel

fun FcmTokenLoginResponse.toModel(): FcmTokenLoginResponseModel {
    return FcmTokenLoginResponseModel(
        accessToken = accessToken,
        accessTokenExpiresIn = accessTokenExpiresIn
    )
}
