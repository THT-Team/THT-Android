package com.tht.tht.data.repository

import android.net.Uri
import com.tht.tht.data.remote.datasource.ImageDataSource
import com.tht.tht.domain.image.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageDataSource: ImageDataSource
) : ImageRepository {

    override suspend fun uploadImageWithIndex(
        uri: String,
        saveFileName: String,
        idx: Int
    ): Pair<String, Int> {
        return imageDataSource.uploadImage(Uri.parse(uri), saveFileName) to idx
    }

    override suspend fun removeImage(fileName: String): Boolean {
        return imageDataSource.removeImage(fileName)
    }
}
