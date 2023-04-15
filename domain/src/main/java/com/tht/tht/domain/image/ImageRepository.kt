package com.tht.tht.domain.image

interface ImageRepository {
    suspend fun uploadImage(uri: String, saveFileName: String): String
}
