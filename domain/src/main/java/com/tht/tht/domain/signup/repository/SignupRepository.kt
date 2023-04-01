package com.tht.tht.domain.signup.repository

import com.tht.tht.domain.signup.model.IdealTypeModel
import com.tht.tht.domain.signup.model.InterestModel
import com.tht.tht.domain.signup.model.SignupUserModel
import com.tht.tht.domain.signup.model.TermsModel

interface SignupRepository {
    suspend fun saveSignupUser(user: SignupUserModel): SignupUserModel

    suspend fun fetchSignupUser(phone: String): SignupUserModel?

    suspend fun patchSignupUser(phone: String, user: SignupUserModel): Boolean

    suspend fun removeSignupUser(phone: String): Boolean

    suspend fun requestAuthentication(phone: String): String

    suspend fun fetchTerms(): List<TermsModel>

    suspend fun checkNicknameDuplicate(nickname: String): Boolean

    suspend fun fetchInterest(): List<InterestModel>

    suspend fun fetchIdealType(): List<IdealTypeModel>

    suspend fun requestSignup(user: SignupUserModel): Boolean
}
