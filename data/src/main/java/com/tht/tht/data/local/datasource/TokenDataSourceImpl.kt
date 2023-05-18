package com.tht.tht.data.local.datasource

import com.tht.tht.data.di.IODispatcher
import com.tht.tht.data.local.dao.TokenDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TokenDataSourceImpl @Inject constructor(
    private val tokenDao: TokenDao,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : TokenDataSource {

    override suspend fun fetchFcmToken(): String? {
        return withContext(dispatcher) {
            tokenDao.fetchFcmToken()
        }
    }

    override suspend fun updateFcmToken(token: String) {
        withContext(dispatcher) {
            tokenDao.updateFcmToken(token)
        }
    }

    override suspend fun updateThtToken(token: String, accessTokenExpiresIn: Int, phone: String) {
        withContext(dispatcher) {
            tokenDao.updateThtToken(token, accessTokenExpiresIn, phone)
        }
    }

    override suspend fun fetchThtToken(): String? {
        return withContext(dispatcher) {
            tokenDao.fetchThtToken()
        }
    }

    override suspend fun fetchPhone(): String? {
        return tokenDao.fetchPhone()
    }
}
