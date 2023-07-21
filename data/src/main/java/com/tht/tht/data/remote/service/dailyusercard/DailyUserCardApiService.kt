package com.tht.tht.data.remote.service.dailyusercard

import com.tht.tht.data.constant.THTApiConstant
import com.tht.tht.data.remote.request.dailyusercard.DailyUserCardRequest
import com.tht.tht.data.remote.response.base.ThtResponse
import com.tht.tht.data.remote.response.dailyusercard.DailyUserCardResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * 1. UserList를 호출
 * 2. 현재 TopicIdx -1 -> TopicList를 호출
 */
interface DailyUserCardApiService {
    @POST(THTApiConstant.Card.DAILY_USER_CARD)
    suspend fun fetchDailyUserCard(
        @Body body: DailyUserCardRequest
    ): ThtResponse<DailyUserCardResponse>
}
