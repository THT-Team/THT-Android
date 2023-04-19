package com.tht.tht.data.remote.datasource

import com.tht.tht.data.di.IODispatcher
import com.tht.tht.data.remote.response.location.LocationResponse
import com.tht.tht.data.remote.service.LocationService
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
}
