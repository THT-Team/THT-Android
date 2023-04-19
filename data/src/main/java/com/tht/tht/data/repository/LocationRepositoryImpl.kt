package com.tht.tht.data.repository

import com.tht.tht.data.local.mapper.toModel
import com.tht.tht.data.remote.datasource.LocationDataSource
import com.tht.tht.domain.signup.model.LocationModel
import com.tht.tht.domain.signup.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationDataSource: LocationDataSource
) : LocationRepository {

    override suspend fun fetchCurrentLocation(): LocationModel =
        locationDataSource.fetchCurrentLocation().toModel()

    override suspend fun fetchLocationByAddress(address: String): LocationModel =
        locationDataSource.fetchLocationByAddress(address).toModel()
}
