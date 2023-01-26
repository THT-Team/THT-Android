package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.model.IdealTypeModel
import com.tht.tht.domain.signup.repository.SignupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class FetchIdealTypeUseCase(
    private val repository: SignupRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Result<List<IdealTypeModel>> {
        return kotlin.runCatching {
            withContext(dispatcher) {
                repository.fetchIdealType()
            }
        }
    }
}
