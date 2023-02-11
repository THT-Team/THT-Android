package com.tht.tht.data.datasource

import com.tht.tht.data.di.IODispatcher
import com.tht.tht.data.local.dao.TermsDao
import com.tht.tht.data.local.entity.TermsEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TermsDataSourceImpl @Inject constructor(
    private val termsDao: TermsDao,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : TermsDataSource {

    override suspend fun fetchSignupTerms(): TermsEntity {
        return withContext(dispatcher) {
            termsDao.fetchTerms()
        }
    }
}
