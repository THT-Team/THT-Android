package com.tht.tht.data.remote.datasource

import com.tht.tht.data.di.IODispatcher
import com.tht.tht.data.remote.mapper.toUnwrap
import com.tht.tht.data.remote.response.regioncode.RegionCodeResponse
import com.tht.tht.data.remote.service.location.RegionCodeApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegionCodeDataSourceImpl @Inject constructor(
    private val regionCodeApi: RegionCodeApi,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : RegionCodeDataSource {

    override suspend fun fetchRegionCode(address: String): RegionCodeResponse {
        return withContext(dispatcher) {
            regionCodeApi.fetchRegionCode(address = address).toUnwrap { it }
        }
    }
}
