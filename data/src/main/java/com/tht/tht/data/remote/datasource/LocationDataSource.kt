package com.tht.tht.data.remote.datasource

import com.tht.tht.data.remote.response.location.LocationResponse

interface LocationDataSource {
    suspend fun fetchCurrentLocation(): LocationResponse
    suspend fun fetchLocationByAddress(address: String): LocationResponse
}
