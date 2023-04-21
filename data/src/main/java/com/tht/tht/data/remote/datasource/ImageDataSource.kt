package com.tht.tht.data.remote.datasource

import android.net.Uri

interface ImageDataSource {
    suspend fun uploadImage(uri: Uri, saveFileName: String): String
}
