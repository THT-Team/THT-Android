package com.tht.tht.data.repository

import com.tht.tht.data.di.DefaultDispatcher
import com.tht.tht.data.local.mapper.toModel
import com.tht.tht.data.remote.datasource.signup.RegionCodeDataSource
import com.tht.tht.domain.signup.model.RegionCodeModel
import com.tht.tht.domain.signup.repository.RegionCodeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegionCodeRepositoryImpl @Inject constructor(
    private val regionCodeDataSource: RegionCodeDataSource,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : RegionCodeRepository {

    override suspend fun fetchRegionCode(address: String): RegionCodeModel {
        return withContext(dispatcher) {
            regionCodeDataSource.fetchRegionCode(address).toModel()
        }
    }
}
