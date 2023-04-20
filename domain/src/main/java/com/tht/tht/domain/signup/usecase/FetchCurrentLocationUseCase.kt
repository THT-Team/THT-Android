package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.model.LocationModel
import com.tht.tht.domain.signup.repository.LocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class FetchCurrentLocationUseCase(
    private val repository: LocationRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Result<LocationModel> {
        return kotlin.runCatching {
            withContext(dispatcher) {
                repository.fetchCurrentLocation()
            }
        }
    }
}
