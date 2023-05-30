package com.tht.tht.data.remote.service.email


interface EmailService {

    suspend fun sendEmail(text: String)
}
