package com.tht.tht.data.remote.service.location

import com.tht.tht.data.remote.response.location.LocationResponse

interface LocationService {
    suspend fun fetchCurrentLocation(): LocationResponse
    suspend fun fetchLocationByAddress(address: String): LocationResponse
}
