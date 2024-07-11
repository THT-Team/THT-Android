package com.tht.tht.data.local.datasource

import com.tht.tht.data.remote.response.terms.TermsResponse

interface TermsDataSource {

    suspend fun fetchSignupTerms(): TermsResponse
}
