package com.tht.tht.domain.email.usecase

import com.tht.tht.domain.email.constant.EmailServiceConstant
import com.tht.tht.domain.email.repository.EmailRepository

class SendInquiryEmailUseCase(
    private val repository: EmailRepository
) {

    suspend operator fun invoke(email: String, content: String) = kotlin.runCatching {
        repository.sendEmail(
            EmailServiceConstant.TITLE,
            "${EmailServiceConstant.CONTENT}$email\n\n\n$content",
            EmailServiceConstant.RECIPIENT
        )
    }
}
