package com.tht.tht.data.datasource

import com.tht.tht.data.local.entity.TermsEntity

interface TermsDataSource {

    suspend fun fetchSignupTerms(): TermsEntity
}
