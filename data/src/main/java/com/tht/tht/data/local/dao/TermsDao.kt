package com.tht.tht.data.local.dao

import com.tht.tht.data.local.entity.TermsEntity

interface TermsDao {
    suspend fun fetchTerms(): TermsEntity
}
