package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.model.LocationModel
import com.tht.tht.domain.signup.repository.LocationRepository

class FetchLocationByAddressUseCase(
    private val repository: LocationRepository
) {
    suspend operator fun invoke(address: String): Result<LocationModel> {
        return kotlin.runCatching {
            repository.fetchLocationByAddress(address)
        }
    }
}
