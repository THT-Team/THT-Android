package tht.feature.chat.chat.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_ui.common.viewmodel.Container
import com.example.compose_ui.common.viewmodel.Store
import com.example.compose_ui.common.viewmodel.intent
import com.example.compose_ui.common.viewmodel.store
import com.tht.tht.domain.chat.usecase.ExitChattingRoomUseCase
import com.tht.tht.domain.chat.usecase.GetChatListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tht.core.navigation.NavBackStackEntryTopic
import tht.core.navigation.getResult
import tht.feature.chat.chat.state.ChatDetailState
import tht.feature.chat.chat.state.ChatSideEffect
import tht.feature.chat.chat.state.ChatState
import tht.feature.chat.mapper.toModel
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
internal class ChatViewModel @Inject constructor(
    private val getChatListUseCase: GetChatListUseCase,
    private val exitChattingRoomUseCase: ExitChattingRoomUseCase,
) : ViewModel(), Container<ChatState, ChatSideEffect> {
    override val store: Store<ChatState, ChatSideEffect> =
        store(initialState = ChatState.ChatList(isLoading = true, chatList = persistentListOf()))

    init {
        getChatList()
    }

    fun collectNavBackStackEntry() = viewModelScope.launch {
        NavBackStackEntryTopic.state.collectLatest { navBackStackEntry ->
            navBackStackEntry.getResult<Long?>()?.let { roomIdx ->
                intent {
                    reduce {
                        val state = (it as? ChatState.ChatList)
                        state?.copy(
                            chatList = state.chatList.toMutableList().apply {
                                find { it.chatRoomIdx == roomIdx }
                                    ?.let { remove(it) }
                            }.toImmutableList()
                        ) ?: it
                    }
                }
            }
        }
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

    fun exitChattingRoom(roomIdx: Long) {
        viewModelScope.launch {
            exitChattingRoomUseCase.invoke(roomIdx)
                .onSuccess {
                    if (store.state.value is ChatState.ChatList) {
                        intent {
                            reduce {
                                val state = (it as? ChatState.ChatList)
                                state?.copy(
                                    chatList = state.chatList.toMutableList().apply {
                                        find { it.chatRoomIdx == roomIdx }
                                            ?.let { remove(it) }
                                    }.toImmutableList()
                                ) ?: it
                            }
                        }
                    }
                }
        }
    }
}
