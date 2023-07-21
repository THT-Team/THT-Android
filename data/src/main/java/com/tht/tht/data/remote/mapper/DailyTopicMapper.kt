package com.tht.tht.data.remote.mapper

import com.tht.tht.data.remote.response.topic.DailyTopicResponse
import com.tht.tht.domain.topic.DailyTopicListModel
import com.tht.tht.domain.topic.DailyTopicModel

fun DailyTopicResponse.toModel(): DailyTopicListModel {
    return DailyTopicListModel(
        topicResetTimeMill = expirationUnixTime * 1000L,
        topics = fallingTopicList.map { it.toModel() }
    )
}

fun DailyTopicResponse.FallingTopic.toModel(): DailyTopicModel {
    return DailyTopicModel(
        idx = idx,
        key = keywordIdx,
        title = keyword,
        content = talkIssue,
        iconUrl = keywordImgUrl
    )
}

fun DailyTopicListModel.toEntity(): DailyTopicResponse {
    return DailyTopicResponse(
        expirationUnixTime = topicResetTimeMill / 1000L,
        fallingTopicList = topics.map { it.toEntity() }
    )
}

fun DailyTopicModel.toEntity(): DailyTopicResponse.FallingTopic {
    return DailyTopicResponse.FallingTopic(
        idx = idx,
        keyword = title,
        keywordIdx = key,
        keywordImgUrl = iconUrl,
        talkIssue = content
    )
}


