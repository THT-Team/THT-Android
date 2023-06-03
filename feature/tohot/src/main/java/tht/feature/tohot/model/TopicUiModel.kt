package tht.feature.tohot.model

import javax.annotation.concurrent.Immutable

@Immutable
data class TopicUiModel(
    val emoji: String,
    val key: Long,
    val title: String,
    val content: String
)

val topics = listOf(
    TopicUiModel(
        emoji = "1F3AE",
        title = "게임",
        key = 1,
        content = "게임 좋아하세요?"
    ),
    TopicUiModel(
        emoji = "1F4DA",
        title = "독서",
        key = 2,
        content = "책읽을래요??"
    ),
    TopicUiModel(
        emoji = "1F3AC",
        title = "영화/드라마",
        key = 3,
        content = "영화 뭐좋아해요?"
    )
)
