package com.tht.tht.data.local.datasource

import com.tht.tht.data.di.IODispatcher
import com.tht.tht.data.remote.mapper.toUnwrap
import com.tht.tht.data.remote.response.terms.TermsResponse
import com.tht.tht.data.remote.service.THTSignupApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TermsDataSourceImpl @Inject constructor(
    private val apiService: THTSignupApi,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : TermsDataSource {

    override suspend fun fetchSignupTerms(): TermsResponse {
        return withContext(dispatcher) {
            apiService.fetchTermsList().toUnwrap()
        }
    }
}
