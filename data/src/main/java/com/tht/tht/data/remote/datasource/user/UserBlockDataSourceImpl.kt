package com.tht.tht.data.remote.datasource.user

import com.tht.tht.data.remote.mapper.toUnwrap
import com.tht.tht.data.remote.service.user.UserBlockApiService
import javax.inject.Inject

class UserBlockDataSourceImpl @Inject constructor(
    private val service: UserBlockApiService
) : UserBlockDataSource {
    override suspend fun blockUser(userUuid: String) {
        return service.blockUser(userUuid).toUnwrap()
    }
}
