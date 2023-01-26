package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.model.InterestModel
import com.tht.tht.domain.signup.repository.SignupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class FetchInterestUseCase(
    private val repository: SignupRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Result<List<InterestModel>> {
        return kotlin.runCatching {
            withContext(dispatcher) {
                repository.fetchInterest()
            }
        }
    }
}
