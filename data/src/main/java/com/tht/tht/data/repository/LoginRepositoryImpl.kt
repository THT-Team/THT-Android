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

    override suspend fun refreshFcmTokenLogin(
        fcmToken: String,
        phone: String
    ): FcmTokenLoginResponseModel {
        return loginDataSource.refreshFcmTokenLogin(
            FcmTokenLoginRequest(
                fcmToken,
                phone
            )
        ).toModel()
    }

    override suspend fun userDisActive(reason: String, feedback: String) {
        loginDataSource.userDisActive(
            reason = reason,
            feedback = feedback
        )
    }
}
