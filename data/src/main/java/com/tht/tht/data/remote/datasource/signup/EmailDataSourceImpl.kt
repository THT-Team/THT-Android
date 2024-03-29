package com.tht.tht.data.remote.datasource.signup

import com.tht.tht.data.di.IODispatcher
import com.tht.tht.data.remote.service.email.EmailService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EmailDataSourceImpl @Inject constructor(
    private val emailService: EmailService,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : EmailDataSource {

    override suspend fun sendEmail(title: String, text: String, recipient: String) {
        withContext(dispatcher) {
            emailService.sendEmail(title, text, recipient)
        }
    }
}
