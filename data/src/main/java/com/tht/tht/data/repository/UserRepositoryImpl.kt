package com.tht.tht.data.repository

import com.tht.tht.data.remote.datasource.user.UserBlockDataSource
import com.tht.tht.data.remote.datasource.user.UserReportDataSource
import com.tht.tht.domain.user.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userReportDataSource: UserReportDataSource,
    private val userBlockDataSource: UserBlockDataSource
) : UserRepository {

    override suspend fun reportUser(userUuid: String, reason: String) {
        return userReportDataSource.reportUser(userUuid, reason)
    }

    override suspend fun blockUser(userUuid: String) {
        return userBlockDataSource.blockUser(userUuid)
    }
}
