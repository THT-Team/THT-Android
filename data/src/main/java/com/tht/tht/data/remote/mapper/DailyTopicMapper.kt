package com.tht.tht.data.remote.mapper

import com.tht.tht.data.remote.response.topic.DailyTopicResponse
import com.tht.tht.domain.topic.DailyTopicListModel
import com.tht.tht.domain.topic.DailyTopicModel

fun DailyTopicResponse.toModel(): DailyTopicListModel {
    return DailyTopicListModel(
        topicResetTimeMill = expirationUnixTime,
        topics = fallingTopicList.map { it.toModel() }
    )
}

fun DailyTopicResponse.FallingTopic.toModel(): DailyTopicModel {
    return DailyTopicModel(
        imageUrl = keywordImgUrl,
        key = keywordIdx,
        title = keyword,
        content = talkIssue,
        iconUrl = keywordImgUrl
    )
}
