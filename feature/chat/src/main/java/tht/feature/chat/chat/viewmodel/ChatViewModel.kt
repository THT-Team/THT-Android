package tht.feature.chat.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_ui.common.viewmodel.Container
import com.example.compose_ui.common.viewmodel.Store
import com.example.compose_ui.common.viewmodel.intent
import com.example.compose_ui.common.viewmodel.store
import com.tht.tht.domain.chat.usecase.GetChatListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import tht.feature.chat.chat.state.ChatSideEffect
import tht.feature.chat.chat.state.ChatState
import tht.feature.chat.mapper.toModel
import javax.inject.Inject

@HiltViewModel
internal class ChatViewModel @Inject constructor(
    private val getChatListUseCase: GetChatListUseCase,
) : ViewModel(), Container<ChatState, ChatSideEffect> {
    override val store: Store<ChatState, ChatSideEffect> =
        store(initialState = ChatState.ChatList(isLoading = true, chatList = persistentListOf()))

    init {
        getChatList()
    }

    private fun getChatList() {
        viewModelScope.launch {
            val chatList = getChatListUseCase().getOrNull() ?: listOf()
            intent {
                reduce {
                    if (chatList.isEmpty()) {
                        ChatState.Empty
                    } else {
                        ChatState.ChatList(
                            isLoading = false,
                            chatList = chatList.map { it.toModel() }.toImmutableList()
                        )
                    }
                }
            }
        }
    }
}
