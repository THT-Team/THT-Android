package com.tht.tht.domain.email

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SendEmailUseCase(
    private val repository: EmailRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(email: String, content: String) = kotlin.runCatching {
        withContext(dispatcher) {
            repository.sendEmail("답변받을 이메일 주소\n$email\n\n\n내용\n$content")
        }
    }
}
