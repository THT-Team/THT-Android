package tht.feature.chat.viewmodel

import androidx.lifecycle.ViewModel
import com.example.compose_ui.common.viewmodel.Container
import com.example.compose_ui.common.viewmodel.Store
import com.example.compose_ui.common.viewmodel.intent
import com.example.compose_ui.common.viewmodel.store
import dagger.hilt.android.lifecycle.HiltViewModel
import tht.feature.chat.viewmodel.sideeffect.ChatSideEffect
import tht.feature.chat.viewmodel.state.ChatState
import tht.feature.chat.viewmodel.state.skeletonChatList
import javax.inject.Inject

@HiltViewModel
internal class ChatViewModel @Inject constructor() : ViewModel(), Container<ChatState, ChatSideEffect> {
    override val store: Store<ChatState, ChatSideEffect> =
        store(initialState = ChatState.ChatList(isLoading = true, chatList = skeletonChatList))

    fun getChatList() {
        intent {
            reduce {
                ChatState.ChatList(isLoading = false, chatList = listOf("아이템1", "아이템1", "아이템1", "아이템1", "아이템1"))
            }
        }
    }
}
