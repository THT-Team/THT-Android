package tht.feature.tohot.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import tht.feature.tohot.R
import kotlin.time.Duration

@Immutable
sealed interface TopicSelectUiModel {
    val id: String
    val introduce: String
    val topicExpiredDuration: Duration

    data class OneTopic(
        val isEvent: Boolean,
        val topic: TopicUiModel,
        override val introduce: String,
        override val topicExpiredDuration: Duration
    ) : TopicSelectUiModel {
        override val id: String = topic.title
    }

    data class TwoTopic(
        val topic1: TopicUiModel,
        val topic2: TopicUiModel,
        override val introduce: String,
        override val topicExpiredDuration: Duration
    ) : TopicSelectUiModel {
        override val id: String = topic1.title
    }

    data class FourTopic(
        val topics: ImmutableList<TopicUiModel>,
        override val introduce: String,
        override val topicExpiredDuration: Duration
    ) : TopicSelectUiModel {
        override val id: String = topics.first().title
    }
}

@Immutable
data class TopicUiModel(
    val iconUrl: String?,
    @DrawableRes val iconRes: Int,
    val idx: Int,
    val key: Int,
    val title: String,
    val content: String
)

val dummyTopics = persistentListOf(
    TopicUiModel(
        iconUrl = "https://www.emojiall.com/en/header-svg/%F0%9F%98%83.svg",
        iconRes = R.drawable.ic_topic_item_pet_38,
        title = "코딩",
        key = 0,
        idx = 0,
        content = "코딩 같이 할래요??"
    ),
    TopicUiModel(
        iconUrl = "https://www.emojiall.com/en/header-svg/%F0%9F%98%83.svg",
        iconRes = R.drawable.ic_topic_item_alone_48,
        title = "게임",
        key = 1,
        idx = 1,
        content = "게임 좋아하세요?"
    ),
    TopicUiModel(
        iconUrl = "https://www.emojiall.com/en/header-svg/%F0%9F%98%83.svg",
        iconRes = R.drawable.ic_topic_item_fashion_48,
        title = "독서",
        key = 2,
        idx = 2,
        content = "책읽을래요??"
    ),
    TopicUiModel(
        iconUrl = "https://www.emojiall.com/en/header-svg/%F0%9F%98%83.svg",
        iconRes = R.drawable.ic_topic_item_fun_48,
        title = "영화/드라마",
        key = 3,
        idx = 3,
        content = "영화 뭐좋아해요?"
    )
)
