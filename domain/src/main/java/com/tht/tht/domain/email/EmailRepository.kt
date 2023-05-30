package com.tht.tht.domain.email

interface EmailRepository {

    suspend fun sendEmail(text: String)
}
