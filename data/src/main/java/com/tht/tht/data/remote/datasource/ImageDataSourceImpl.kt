package com.tht.tht.data.remote.datasource

import android.net.Uri
import com.tht.tht.data.di.IODispatcher
import com.tht.tht.data.remote.service.image.ImageService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageDataSourceImpl @Inject constructor(
    private val imageService: ImageService,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : ImageDataSource {
    override suspend fun uploadImage(uri: Uri, saveFileName: String): String {
        return withContext(dispatcher) {
            imageService.uploadImage(uri, saveFileName)
        }
    }

    override suspend fun removeImage(fileName: String): Boolean {
        return withContext(dispatcher) {
            imageService.removeImage(fileName)
        }
    }
}
