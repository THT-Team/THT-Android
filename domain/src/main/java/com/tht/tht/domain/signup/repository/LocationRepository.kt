package com.tht.tht.domain.signup.repository

import com.tht.tht.domain.signup.model.LocationModel

interface LocationRepository {

    suspend fun fetchCurrentLocation(): LocationModel
}
