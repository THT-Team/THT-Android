package com.tht.tht.data.datasource

import com.tht.tht.data.remote.response.ideal.IdealTypeResponse
import com.tht.tht.data.remote.response.interests.InterestTypeResponse
import com.tht.tht.data.remote.response.signup.SignupResponse
import com.tht.tht.domain.signup.model.SignupUserModel

interface SignupApiDataSource {
    suspend fun requestAuthenticationNumber(phone: String): Boolean

    suspend fun requestVerify(phone: String, authNumber: String): Boolean

    suspend fun fetchInterests(): List<InterestTypeResponse>

    suspend fun fetchIdealTypes(): List<IdealTypeResponse>

    suspend fun requestSignup(user: SignupUserModel): SignupResponse
}