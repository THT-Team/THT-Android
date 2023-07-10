package com.tht.tht.data.remote.service.topic

import com.tht.tht.data.constant.THTApiConstant
import com.tht.tht.data.remote.response.base.ThtResponse
import com.tht.tht.data.remote.response.topic.DailyTopicResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DailyTopicApiService {
    @GET(THTApiConstant.Topic.DAILY_TOPIC_LIST)
    suspend fun fetchDailyTopic(): ThtResponse<DailyTopicResponse>

    @POST(THTApiConstant.Topic.SELECT_DAILY_TOPIC)
    suspend fun selectDailyTopic(
        @Path("daily-falling-idx") topicIdx: Int
    ): ThtResponse<Unit>
}
