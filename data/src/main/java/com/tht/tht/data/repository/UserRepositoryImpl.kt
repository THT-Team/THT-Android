package com.tht.tht.data.repository

import com.tht.tht.data.remote.datasource.user.UserBlockDataSource
import com.tht.tht.data.remote.datasource.user.UserHeartDataSource
import com.tht.tht.data.remote.datasource.user.UserReportDataSource
import com.tht.tht.domain.user.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userReportDataSource: UserReportDataSource,
    private val userBlockDataSource: UserBlockDataSource,
    private val userHeartDataSource: UserHeartDataSource
) : UserRepository {

    override suspend fun reportUser(userUuid: String, reason: String) {
        return userReportDataSource.reportUser(userUuid, reason)
    }

    override suspend fun blockUser(userUuid: String) {
        return userBlockDataSource.blockUser(userUuid)
    }

    override suspend fun sendHeart(
        userUuid: String,
        selectDailyTopicIdx: Int
    ): Boolean {
        return userHeartDataSource.sendHeart(userUuid, selectDailyTopicIdx).isMatching
    }

    override suspend fun sendDislike(userUuid: String) {
        return userHeartDataSource.sendDislike(userUuid)
    }
}
