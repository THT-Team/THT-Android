package tht.feature.tohot.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import tht.feature.tohot.R

@Immutable
data class TopicUiModel(
    val iconUrl: String?,
    @DrawableRes val iconRes: Int,
    val idx: Int,
    val key: Int,
    val title: String,
    val content: String
)

val topics = listOf(
    TopicUiModel(
        iconUrl = "1F3AE",
        iconRes = R.drawable.ic_topic_item_alone_48,
        title = "게임",
        key = 1,
        idx = 1,
        content = "게임 좋아하세요?"
    ),
    TopicUiModel(
        iconUrl = "1F4DA",
        iconRes = R.drawable.ic_topic_item_fashion_48,
        title = "독서",
        key = 2,
        idx = 2,
        content = "책읽을래요??"
    ),
    TopicUiModel(
        iconUrl = "1F3AC",
        iconRes = R.drawable.ic_topic_item_fun_48,
        title = "영화/드라마",
        key = 3,
        idx = 3,
        content = "영화 뭐좋아해요?"
    )
)
