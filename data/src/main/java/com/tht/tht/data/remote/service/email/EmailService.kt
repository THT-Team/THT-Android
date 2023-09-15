package com.tht.tht.data.remote.service.email

interface EmailService {

    fun sendEmail(title: String, text: String, recipient: String)
}
