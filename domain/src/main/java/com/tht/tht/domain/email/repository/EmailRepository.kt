package com.tht.tht.domain.email.repository

interface EmailRepository {

    suspend fun sendEmail(title: String, text: String, recipient: String)
}
