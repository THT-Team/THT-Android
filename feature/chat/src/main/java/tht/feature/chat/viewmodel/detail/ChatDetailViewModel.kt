package tht.feature.chat.viewmodel.detail

import androidx.lifecycle.ViewModel
import com.example.compose_ui.common.viewmodel.Container
import com.example.compose_ui.common.viewmodel.Store
import com.example.compose_ui.common.viewmodel.intent
import com.example.compose_ui.common.viewmodel.store
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import tht.feature.chat.viewmodel.detail.sideeffect.ChatDetailSideEffect
import tht.feature.chat.viewmodel.detail.state.ChatDetailState
import tht.feature.chat.viewmodel.state.skeletonChatList
import javax.inject.Inject

@HiltViewModel
internal class ChatDetailViewModel @Inject constructor() :
    ViewModel(),
    Container<ChatDetailState, ChatDetailSideEffect> {
    override val store: Store<ChatDetailState, ChatDetailSideEffect> =
        store(
            initialState = ChatDetailState.ChatList(
                isLoading = true,
                chatList = skeletonChatList,
            ),
        )

    private var _currentText: MutableStateFlow<String> = MutableStateFlow("")
    val currentText = _currentText.asStateFlow()
    fun getChatList() {
        intent {
            reduce {
                ChatDetailState.ChatList(
                    isLoading = false,
                    chatList = persistentListOf(
                        "안녕하세요!",
                        "만나서 반가워요~",
                        "긴 텍스트 세줄 이상 문장은 이렇게씁니다아아아아아아아아아아아아아아아아아아아아아긴 텍스트 세줄 이상 문장은 이렇게씁니다아",
                        "만나서 반가워요~",
                        "긴 텍스트 두줄 이상 문장은 이렇게 씁니다아아아아아아아아아",
                        "긴 텍스트 세줄 이상 문장은 이렇게씁니다아아아아아아아아아아아아아아아아아아아아아긴 텍스트 세줄 이상 문장은 이렇게씁니다아.아아아아아아아아아아긴 텍스트 세줄 이상 문장은 이렇게씁니다아", // ktlint-disable max-line-length
                        "긴 텍스트 세줄 이상 문장은 이렇게씁니다아아아아아아아아아아아아아아아아아아아아아긴 텍스트 세줄 이상 문장은 이렇게씁니다아.아아아아아아아아아아긴 텍스트 세줄 이상 문장은 이렇게씁니다아", // ktlint-disable max-line-length
                        "긴 텍스트 세줄 이상 문장은 이렇게씁니다아아아아아아아아아아아아아아아아아아아아아긴 텍스트 세줄 이상 문장은 이렇게씁니다아.아아아아아아아아아아긴 텍스트 세줄 이상 문장은 이렇게씁니다아", // ktlint-disable max-line-length
                        "긴 텍스트 세줄 이상 문장은 이렇게씁니다아아아아아아아아아아아아아아아아아아아아아긴 텍스트 세줄 이상 문장은 이렇게씁니다아.아아아아아아아아아아긴 텍스트 세줄 이상 문장은 이렇게씁니다아", // ktlint-disable max-line-length
                        "긴 텍스트 세줄 이상 문장은 이렇게씁니다아아아아아아아아아아아아아아아아아아아아아긴 텍스트 세줄 이상 문장은 이렇게씁니다아.아아아아아아아아아아긴 텍스트 세줄 이상 문장은 이렇게씁니다아", // ktlint-disable max-line-length
                        "긴 텍스트 세줄 이상 문장은 이렇게씁니다아아아아아아아아아아아아아아아아아아아아아긴 텍스트 세줄 이상 문장은 이렇게씁니다아.아아아아아아아아아아긴 텍스트 세줄 이상 문장은 이렇게씁니다아", // ktlint-disable max-line-length
                        "긴 텍스트 세줄 이상 문장은 이렇게씁니다아아아아아아아아아아아아아아아아아아아아아긴 텍스트 세줄 이상 문장은 이렇게씁니다아.아아아아아아아아아아긴 텍스트 세줄 이상 문장은 이렇게씁니다아", // ktlint-disable max-line-length
                        "긴 텍스트 세줄 이상 문장은 이렇게씁니다아아아아아아아아아아아아아아아아아아아아아긴 텍스트 세줄 이상 문장은 이렇게씁니다아.아아아아아아아아아아긴 텍스트 세줄 이상 문장은 이렇게씁니다아", // ktlint-disable max-line-length
                    ),
                )
            }
        }
    }

    fun updateCurrentText(text: String) {
        _currentText.update { text }
    }

    fun onClickSent(text: String) {}

    fun onClickGallery() {}
}
