package tht.feature.tohot.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import tht.feature.tohot.R

@Immutable
data class TopicUiModel(
    val imageUrl: String?,
    @DrawableRes val imageRes: Int,
    val key: Int,
    val title: String,
    val content: String,
    val iconUrl: String,
    val iconRes: Int
)

val topics = listOf(
    TopicUiModel(
        imageUrl = "1F3AE",
        imageRes = R.drawable.ic_topic_item_alone_48,
        title = "게임",
        key = 1,
        content = "게임 좋아하세요?",
        iconUrl = "123",
        iconRes = R.drawable.ic_topic_item_values_38
    ),
    TopicUiModel(
        imageUrl = "1F4DA",
        imageRes = R.drawable.ic_topic_item_fashion_48,
        title = "독서",
        key = 2,
        content = "책읽을래요??",
        iconUrl = "123",
        iconRes = R.drawable.ic_topic_item_rest_38
    ),
    TopicUiModel(
        imageUrl = "1F3AC",
        imageRes = R.drawable.ic_topic_item_fun_48,
        title = "영화/드라마",
        key = 3,
        content = "영화 뭐좋아해요?",
        iconUrl = "123",
        iconRes = R.drawable.ic_topic_item_values_38
    )
)
