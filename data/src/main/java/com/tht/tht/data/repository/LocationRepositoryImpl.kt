package com.tht.tht.data.repository

import com.tht.tht.data.di.IODispatcher
import com.tht.tht.data.local.mapper.toModel
import com.tht.tht.data.remote.datasource.signup.LocationDataSource
import com.tht.tht.domain.signup.model.LocationModel
import com.tht.tht.domain.signup.repository.LocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val locationDataSource: LocationDataSource
) : LocationRepository {

    override suspend fun fetchCurrentLocation(): LocationModel {
        return withContext(dispatcher) {
            locationDataSource.fetchCurrentLocation().toModel()
        }
    }

    override suspend fun fetchLocationByAddress(address: String): LocationModel {
        return withContext(dispatcher) {
            locationDataSource.fetchLocationByAddress(address).toModel()
        }
    }
}
