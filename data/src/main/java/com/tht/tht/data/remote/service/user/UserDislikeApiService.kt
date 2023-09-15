package com.tht.tht.data.remote.service.user

import retrofit2.http.Path

interface UserDislikeApiService {
    suspend fun sendDislike(
        @Path("dislike-user-uuid") userUuid: String
    )
}
