package com.tht.tht.data.remote.datasource

interface EmailDataSource {

    suspend fun sendEmail(title: String, text: String, recipient: String)
}
