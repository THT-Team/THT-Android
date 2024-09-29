package com.tht.tht.domain.topic

data class DailyTopicListModel(
    val topicResetTimeMill: Long,
    val introduction: String,
    val topicSelectType: TopicSelectType,
    val topics: List<DailyTopicModel>
) {
    enum class TopicSelectType {
        ONE_CHOICE,
        TWO_CHOICE,
        FOUR_CHOICE
    }
}
