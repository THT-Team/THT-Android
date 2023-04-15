package com.tht.tht.data.repository

import android.net.Uri
import com.tht.tht.data.remote.datasource.ImageDataSource
import com.tht.tht.domain.image.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageDataSource: ImageDataSource
) : ImageRepository {

    override suspend fun uploadImage(uri: String, saveFileName: String): String {
        return imageDataSource.uploadImage(Uri.parse(uri), saveFileName)
    }

}
