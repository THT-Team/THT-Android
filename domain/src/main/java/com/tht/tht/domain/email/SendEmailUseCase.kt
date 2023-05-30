package com.tht.tht.domain.email

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SendEmailUseCase(
    private val repository: EmailRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(email: String, content: String) = kotlin.runCatching {
        withContext(dispatcher) {
            repository.sendEmail("$email\n\n$content")
        }
    }
}
