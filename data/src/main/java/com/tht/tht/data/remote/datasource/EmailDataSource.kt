package com.tht.tht.data.remote.datasource

interface EmailDataSource {

    suspend fun sendEmail(text: String)
}
