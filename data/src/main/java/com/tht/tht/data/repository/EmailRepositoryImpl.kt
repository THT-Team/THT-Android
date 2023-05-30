package com.tht.tht.data.repository

import com.tht.tht.data.di.IODispatcher
import com.tht.tht.data.remote.datasource.EmailDataSource
import com.tht.tht.domain.email.EmailRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EmailRepositoryImpl @Inject constructor(
    private val datasource: EmailDataSource,
    @IODispatcher private val dispatcher: CoroutineDispatcher
): EmailRepository {

    override suspend fun sendEmail(text: String) {
        withContext(dispatcher) {
            datasource.sendEmail(text)
        }
    }
}
