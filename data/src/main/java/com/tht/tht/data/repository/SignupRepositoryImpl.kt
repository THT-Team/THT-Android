package com.tht.tht.data.repository

import com.tht.tht.data.di.DefaultDispatcher
import com.tht.tht.data.local.datasource.SignupUserDataSource
import com.tht.tht.data.local.datasource.TermsDataSource
import com.tht.tht.data.local.mapper.toEntity
import com.tht.tht.data.local.mapper.toModel
import com.tht.tht.data.remote.datasource.SignupApiDataSource
import com.tht.tht.data.remote.mapper.toModel
import com.tht.tht.domain.signup.model.IdealTypeModel
import com.tht.tht.domain.signup.model.InterestModel
import com.tht.tht.domain.signup.model.SignupCheckModel
import com.tht.tht.domain.signup.model.SignupResponseModel
import com.tht.tht.domain.signup.model.SignupUserModel
import com.tht.tht.domain.signup.model.TermsModel
import com.tht.tht.domain.signup.repository.SignupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignupRepositoryImpl @Inject constructor(
    private val signupApiDataSource: SignupApiDataSource,
    private val signupUserDataSource: SignupUserDataSource,
    private val termsDataSource: TermsDataSource,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : SignupRepository {
    override suspend fun saveSignupUser(user: SignupUserModel): SignupUserModel {
        return withContext(dispatcher) {
            signupUserDataSource.saveSignupUser(user.phone, user.toEntity()).toModel()
        }
    }

    override suspend fun fetchSignupUser(phone: String): SignupUserModel? {
        return withContext(dispatcher) {
            signupUserDataSource.fetchSignupUser(phone)?.toModel()
        }
    }

    override suspend fun patchSignupUser(phone: String, user: SignupUserModel): Boolean {
        return withContext(dispatcher) {
            signupUserDataSource.saveSignupUser(phone, user.toEntity()).toModel() == user
        }
    }

    override suspend fun removeSignupUser(phone: String): Boolean {
        return withContext(dispatcher) {
            signupUserDataSource.removeSignupUser(phone)
        }
    }

    override suspend fun requestAuthentication(phone: String): String {
        return withContext(dispatcher) {
            signupApiDataSource.requestAuthenticationNumber(phone).authNumber.toString()
        }
    }

    override suspend fun fetchTerms(): List<TermsModel> {
        return withContext(dispatcher) {
            termsDataSource.fetchSignupTerms().body.map { it.toModel() }
        }
    }

    override suspend fun checkNicknameDuplicate(nickname: String): Boolean {
        return withContext(dispatcher) {
            signupApiDataSource.checkNicknameDuplicate(nickname)
        }
    }

    override suspend fun fetchInterest(): List<InterestModel> {
        return withContext(dispatcher) {
            signupApiDataSource.fetchInterests().map { it.toModel() }
        }
    }

    override suspend fun fetchIdealType(): List<IdealTypeModel> {
        return withContext(dispatcher) {
            signupApiDataSource.fetchIdealTypes().map { it.toModel() }
        }
    }

    override suspend fun requestSignup(user: SignupUserModel): SignupResponseModel {
        return withContext(dispatcher) {
            signupApiDataSource.requestSignup(user).toModel()
        }
    }

    override suspend fun checkSignupState(phone: String): SignupCheckModel {
        return withContext(dispatcher) {
            signupApiDataSource.checkLoginState(phone).toModel()
        }
    }
}
