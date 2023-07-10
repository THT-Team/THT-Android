package com.tht.tht.data.remote.service.image

import android.net.Uri

interface ImageService {
    suspend fun uploadImage(uri: Uri, saveFileName: String): String

    suspend fun removeImage(fileName: String): Boolean
}
