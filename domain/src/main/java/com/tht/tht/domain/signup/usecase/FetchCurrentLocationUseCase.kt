package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.model.LocationModel
import com.tht.tht.domain.signup.repository.LocationRepository

class FetchCurrentLocationUseCase(
    private val repository: LocationRepository
) {
    suspend operator fun invoke(): Result<LocationModel> {
        return kotlin.runCatching {
            repository.fetchCurrentLocation()
        }
    }
}
