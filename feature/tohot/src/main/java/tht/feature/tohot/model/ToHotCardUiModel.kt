package tht.feature.tohot.model

import androidx.compose.runtime.Immutable

@Immutable
sealed interface ToHotCardUiModel {
    val id: String

    @Immutable
    data class Topic(
        val topic: TopicSelectUiModel
    ) : ToHotCardUiModel {
        override val id: String = topic.id
    }

    @Immutable
    data class User(
        val user: ToHotUserUiModel
    ) : ToHotCardUiModel {
        override val id = user.id
    }
}
