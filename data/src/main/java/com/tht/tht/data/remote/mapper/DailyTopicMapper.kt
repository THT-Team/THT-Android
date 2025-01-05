package com.tht.tht.data.remote.mapper

import com.tht.tht.data.remote.response.topic.DailyTopicResponse
import com.tht.tht.domain.tohot.DailyTopicListModel
import com.tht.tht.domain.tohot.DailyTopicModel

fun DailyTopicResponse.toModel(): DailyTopicListModel {
    return DailyTopicListModel(
        topicResetTimeMill = expirationUnixTime * 1000L,
        introduction = introduction,
        topicSelectType = when (type) {
            DailyTopicResponse.TopicSelectType.ONE_CHOICE -> DailyTopicListModel.TopicSelectType.ONE_CHOICE
            DailyTopicResponse.TopicSelectType.TWO_CHOICE -> DailyTopicListModel.TopicSelectType.TWO_CHOICE
            DailyTopicResponse.TopicSelectType.FOUR_CHOICE -> DailyTopicListModel.TopicSelectType.FOUR_CHOICE
        },
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
        introduction = introduction,
        type = when (topicSelectType) {
            DailyTopicListModel.TopicSelectType.ONE_CHOICE -> DailyTopicResponse.TopicSelectType.ONE_CHOICE
            DailyTopicListModel.TopicSelectType.TWO_CHOICE -> DailyTopicResponse.TopicSelectType.TWO_CHOICE
            DailyTopicListModel.TopicSelectType.FOUR_CHOICE -> DailyTopicResponse.TopicSelectType.FOUR_CHOICE
        },
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
