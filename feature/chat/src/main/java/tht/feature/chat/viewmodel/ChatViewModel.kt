package tht.feature.chat.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_ui.common.viewmodel.Container
import com.example.compose_ui.common.viewmodel.Store
import com.example.compose_ui.common.viewmodel.intent
import com.example.compose_ui.common.viewmodel.store
import com.tht.tht.domain.chat.usecase.GetChatListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import tht.feature.chat.viewmodel.sideeffect.ChatSideEffect
import tht.feature.chat.viewmodel.state.ChatState
import tht.feature.chat.viewmodel.state.skeletonChatList
import javax.inject.Inject

@HiltViewModel
internal class ChatViewModel @Inject constructor(
    private val getChatListUseCase: GetChatListUseCase,
) : ViewModel(), Container<ChatState, ChatSideEffect> {
    override val store: Store<ChatState, ChatSideEffect> =
        store(initialState = ChatState.ChatList(isLoading = true, chatList = skeletonChatList))

    fun getChatList() {
        viewModelScope.launch {
            val chatList = getChatListUseCase().getOrNull() ?: listOf()
            intent {
                reduce {
                    ChatState.ChatList(
                        isLoading = false,
                        chatList = chatList.toImmutableList(),
                    )
                }
            }
        }
    }
}
