package com.tht.tht.data.remote.service.email

import javax.inject.Inject

class EmailServiceImpl @Inject constructor(
    private val eMailSender: EMailSender
) : EmailService {

    override fun sendEmail(title: String, text: String, recipient: String) {
        eMailSender.sendMail(title, text, recipient)
    }
}
