package com.tht.tht.data.remote.datasource.user

interface UserBlockDataSource {
    suspend fun blockUser(userUuid: String)
}
