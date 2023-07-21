package com.tht.tht.domain.user

interface UserRepository {
    suspend fun reportUser(userUuid: String, reason: String)

    suspend fun blockUser(userUuid: String)
}
