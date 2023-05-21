package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.model.RegionCodeModel
import com.tht.tht.domain.signup.repository.RegionCodeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class FetchRegionCodeUseCase(
    private val repository: RegionCodeRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(address: String): Result<RegionCodeModel> {
        return kotlin.runCatching {
            withContext(dispatcher) {
                repository.fetchRegionCode(address)
            }
        }
    }
}
