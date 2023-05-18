package com.tht.tht.domain.login.repository

import com.tht.tht.domain.token.model.FcmTokenLoginResponseModel

interface LoginRepository {

    suspend fun requestFcmTokenLogin(fcmToken: String, phone: String): FcmTokenLoginResponseModel
}
