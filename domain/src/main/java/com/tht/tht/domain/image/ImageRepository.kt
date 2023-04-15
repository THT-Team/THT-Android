package com.tht.tht.domain.image

interface ImageRepository {
    suspend fun uploadImageWithIndex(uri: String, saveFileName: String, idx: Int): Pair<String, Int>
}
