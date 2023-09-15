package com.tht.tht.data.local.datasource

import com.tht.tht.data.di.IODispatcher
import com.tht.tht.data.local.dao.SignupUserDao
import com.tht.tht.data.local.entity.SignupUserEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignupUserDataSourceImpl @Inject constructor(
    private val signupUserDao: SignupUserDao,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : SignupUserDataSource {
    override suspend fun saveSignupUser(phone: String, user: SignupUserEntity): SignupUserEntity {
        return withContext(dispatcher) {
            signupUserDao.saveSignupUser(phone, user)
        }
    }

    override suspend fun fetchSignupUser(phone: String): SignupUserEntity? {
        return withContext(dispatcher) {
            signupUserDao.fetchSignupUser(phone)
        }
    }

    override suspend fun removeSignupUser(phone: String): Boolean {
        return withContext(dispatcher) {
            signupUserDao.removeSignupUser(phone)
        }
    }
}
