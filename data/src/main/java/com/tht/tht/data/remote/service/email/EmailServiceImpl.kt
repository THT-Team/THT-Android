package com.tht.tht.data.remote.service.email

import com.tht.tht.data.BuildConfig
import com.tht.tht.data.constant.EmailServiceConstant
import javax.inject.Inject


class EmailServiceImpl @Inject constructor(
): EmailService {

    override suspend fun sendEmail(text: String) {
        EMailSender().sendMail(EmailServiceConstant.TITLE, text, BuildConfig.CEO_EMAIL)
    }
}
