package tht.feature.tohot.model

import androidx.compose.runtime.Immutable
import com.tht.tht.domain.tohot.ToHotCardModel
import java.util.UUID

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

    // 페이징 중 다음 유저 없는 경우
    @Immutable
    data object NoneNextUser : ToHotCardUiModel {
        override val id = "NoneNextUser_${UUID.randomUUID()}"
    }

    @Immutable
    data class Error(
        val errorType: ErrorType
    ) : ToHotCardUiModel {
        override val id: String = "Error${UUID.randomUUID()}"

        sealed interface ErrorType {
            data class Custom(val error: Throwable) : ErrorType
            data object NoneUserIndex : ErrorType
        }
    }
}

fun List<ToHotCardUiModel>.hasAnyUser(): Boolean {
    return this.any { it is ToHotCardUiModel.User }
}
