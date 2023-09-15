package com.tht.tht.data.remote.datasource.signup

import com.tht.tht.data.di.IODispatcher
import com.tht.tht.data.remote.response.location.LocationResponse
import com.tht.tht.data.remote.service.location.LocationService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationDataSourceImpl @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val locationService: LocationService
) : LocationDataSource {

    override suspend fun fetchCurrentLocation(): LocationResponse {
        return withContext(dispatcher) {
            locationService.fetchCurrentLocation()
        }
    }

    override suspend fun fetchLocationByAddress(address: String): LocationResponse {
        return withContext(dispatcher) {
            locationService.fetchLocationByAddress(address)
        }
    }
}
