package com.tht.tht.data.repository

import com.tht.tht.data.remote.datasource.signup.EmailDataSource
import com.tht.tht.domain.email.repository.EmailRepository
import javax.inject.Inject

class EmailRepositoryImpl @Inject constructor(
    private val datasource: EmailDataSource
) : EmailRepository {

    override suspend fun sendEmail(title: String, text: String, recipient: String) {
        datasource.sendEmail(title, text, recipient)
    }
}
