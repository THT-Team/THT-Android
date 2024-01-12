package com.tht.tht.data.remote.datasource.user

interface UserReportDataSource {
    suspend fun reportUser(userUuid: String, reason: String)
}
