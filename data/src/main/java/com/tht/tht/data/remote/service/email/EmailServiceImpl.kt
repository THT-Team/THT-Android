package com.tht.tht.data.remote.service.email

import com.tht.tht.data.BuildConfig
import com.tht.tht.data.constant.EmailServiceConstant


class EmailServiceImpl : EmailService {

    override suspend fun sendEmail(text: String) {
        EMailSender().sendMail(EmailServiceConstant.TITLE, text, BuildConfig.CEO_EMAIL)
    }
}
