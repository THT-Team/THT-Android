package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.model.SignupException
import com.tht.tht.domain.signup.repository.SignupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PatchLocationUseCase(
    private val fetchRegionCodeUseCase: FetchRegionCodeUseCase,
    private val repository: SignupRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(
        phone: String,
        lat: Double,
        lng: Double,
        address: String
    ): Result<Boolean> {
        return kotlin.runCatching {
            if (lat < 0.0 || lng < 0.0 || address.isBlank()) {
                throw SignupException.InvalidateLocationInfo()
            }
            withContext(dispatcher) {
                val regionCode = fetchRegionCodeUseCase(address).getOrThrow().regionCode
                repository.patchSignupUser(
                    phone,
                    requireNotNull(repository.fetchSignupUser(phone)) {
                        throw SignupException.SignupUserInvalidateException()
                    }.copy(
                        lat = lat,
                        lng = lng,
                        address = address,
                        regionCode = regionCode
                    )
                )
            }
        }
    }
}
