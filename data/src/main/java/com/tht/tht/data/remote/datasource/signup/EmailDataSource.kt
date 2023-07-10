package com.tht.tht.data.remote.datasource.signup

interface EmailDataSource {

    suspend fun sendEmail(title: String, text: String, recipient: String)
}
