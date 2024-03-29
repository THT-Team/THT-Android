package com.tht.tht.data.local.service

import android.content.Context
import com.google.gson.Gson
import com.tht.tht.data.local.entity.TermsEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TermsServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : TermsService {

    override fun fetchTerms(): TermsEntity {
        return try {
            val inputStream = context.resources.assets.open("signup_terms.json")
            val reader = inputStream.bufferedReader()
            Gson().fromJson(reader, TermsEntity::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}
