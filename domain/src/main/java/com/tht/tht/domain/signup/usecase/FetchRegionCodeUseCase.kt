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
                repository.fetchRegionCode(
                    address.split(" ").run {
                        val index = when(this[0]) {
                            "부산광역시", "대구광역시", "인천광역시", "광주광역시",
                            "대전광역시", "울산광역시", "서울특별시" -> 2
                            else -> 3
                        }
                        take(index)
                    }.joinToString(" ")
                )
            }
        }
    }
}
