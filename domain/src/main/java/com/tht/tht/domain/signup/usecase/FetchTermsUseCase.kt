package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.model.TermsModel
import com.tht.tht.domain.signup.repository.SignupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class FetchTermsUseCase(
    private val repository: SignupRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Result<List<TermsModel>> {
        return kotlin.runCatching {
            withContext(dispatcher) {
                repository.fetchTerms()
            }
        }
    }
}
