package com.tht.tht.data.remote.datasource

import com.tht.tht.data.di.IODispatcher
import com.tht.tht.data.remote.service.email.EmailService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EmailDataSourceImpl @Inject constructor(
    private val emailService: EmailService,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : EmailDataSource {

    override suspend fun sendEmail(text: String) {
        withContext(dispatcher) {
            emailService.sendEmail(text)
        }
    }
}
