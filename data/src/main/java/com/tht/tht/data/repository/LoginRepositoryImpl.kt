package com.tht.tht.data.repository

import com.tht.tht.data.remote.datasource.login.LoginDataSource
import com.tht.tht.data.remote.mapper.toModel
import com.tht.tht.data.remote.request.login.FcmTokenLoginRequest
import com.tht.tht.domain.login.repository.LoginRepository
import com.tht.tht.domain.token.model.FcmTokenLoginResponseModel
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource
) : LoginRepository {

    override suspend fun requestFcmTokenLogin(fcmToken: String, phone: String): FcmTokenLoginResponseModel {
        return loginDataSource.requestFcmTokenLogin(
            FcmTokenLoginRequest(
                fcmToken,
                phone
            )
        ).toModel()
    }
}
