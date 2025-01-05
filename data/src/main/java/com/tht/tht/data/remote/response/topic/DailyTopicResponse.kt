package com.tht.tht.data.remote.response.topic

import com.google.gson.annotations.SerializedName

data class DailyTopicResponse(
    @SerializedName("expirationUnixTime")
    val expirationUnixTime: Long,
    @SerializedName("introduction")
    val introduction: String,
    @SerializedName("type")
    val type: TopicSelectType,
    @SerializedName("fallingTopicList")
    val fallingTopicList: List<FallingTopic>
) {
    data class FallingTopic(
        @SerializedName("idx")
        val idx: Int,
        @SerializedName("keyword")
        val keyword: String,
        @SerializedName("keywordIdx")
        val keywordIdx: Int,
        @SerializedName("keywordImgUrl")
        val keywordImgUrl: String,
        @SerializedName("talkIssue")
        val talkIssue: String
    )

    enum class TopicSelectType {
        @SerializedName("oneChoice")
        ONE_CHOICE,

        @SerializedName("twoChoice")
        TWO_CHOICE,

        @SerializedName("fourChoice")
        FOUR_CHOICE
    }
}
