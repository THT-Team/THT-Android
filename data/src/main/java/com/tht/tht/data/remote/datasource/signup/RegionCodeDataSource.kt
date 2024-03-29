package com.tht.tht.data.remote.datasource.signup

import com.tht.tht.data.remote.response.regioncode.RegionCodeResponse

interface RegionCodeDataSource {
    suspend fun fetchRegionCode(address: String): RegionCodeResponse
}
