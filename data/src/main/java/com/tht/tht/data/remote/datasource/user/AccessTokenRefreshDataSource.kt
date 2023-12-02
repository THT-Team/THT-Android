package com.tht.tht.data.remote.datasource.user

import com.tht.tht.data.remote.response.user.AccessTokenRefreshResponse

interface AccessTokenRefreshDataSource {
    suspend fun refreshAccessToken(): AccessTokenRefreshResponse
}
