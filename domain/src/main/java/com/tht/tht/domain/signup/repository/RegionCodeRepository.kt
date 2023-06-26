package com.tht.tht.domain.signup.repository

import com.tht.tht.domain.signup.model.RegionCodeModel

interface RegionCodeRepository {
    suspend fun fetchRegionCode(address: String): RegionCodeModel
}
