package com.tht.tht.data.remote.datasource.user

import com.tht.tht.data.remote.mapper.toUnwrap
import com.tht.tht.data.remote.request.user.UserReportRequest
import com.tht.tht.data.remote.service.user.UserReportApiService
import javax.inject.Inject

class UserReportDataSourceImpl @Inject constructor(
    private val service: UserReportApiService
) : UserReportDataSource {
    override suspend fun reportUser(userUuid: String, reason: String) {
        return service.reportUser(UserReportRequest(userUuid, reason)).toUnwrap()
    }
}
