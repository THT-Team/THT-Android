package com.tht.tht.data.remote.datasource.dailyusercard

import com.tht.tht.data.remote.mapper.toUnwrap
import com.tht.tht.data.remote.request.dailyusercard.DailyUserCardRequest
import com.tht.tht.data.remote.response.dailyusercard.DailyUserCardResponse
import com.tht.tht.data.remote.service.dailyusercard.DailyUserCardApiService
import javax.inject.Inject

class DailyUserCardDataSourceImpl @Inject constructor(
    private val service: DailyUserCardApiService
) : DailyUserCardDataSource {
    override suspend fun fetchDailyUserCard(request: DailyUserCardRequest): DailyUserCardResponse {
        return service.fetchDailyUserCard(request).toUnwrap { it }
    }
}
