package com.tht.tht.data.remote.datasource.user

import com.tht.tht.data.di.IODispatcher
import com.tht.tht.data.remote.mapper.toUnwrap
import com.tht.tht.data.remote.response.user.AccessTokenRefreshResponse
import com.tht.tht.data.remote.service.user.AccessTokenRefreshService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccessTokenRefreshDataSourceImpl @Inject constructor(
    private val accessTokenRefreshService: AccessTokenRefreshService,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : AccessTokenRefreshDataSource {

    override suspend fun refreshAccessToken(): AccessTokenRefreshResponse {
        return withContext(dispatcher) {
            accessTokenRefreshService.refreshAccessToken().toUnwrap()
        }
    }
}
