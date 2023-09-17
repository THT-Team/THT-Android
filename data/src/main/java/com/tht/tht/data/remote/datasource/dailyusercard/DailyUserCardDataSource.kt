package com.tht.tht.data.remote.datasource.dailyusercard

import com.tht.tht.data.remote.request.dailyusercard.DailyUserCardRequest
import com.tht.tht.data.remote.response.dailyusercard.DailyUserCardResponse

interface DailyUserCardDataSource {
    suspend fun fetchDailyUserCard(request: DailyUserCardRequest): DailyUserCardResponse
}
