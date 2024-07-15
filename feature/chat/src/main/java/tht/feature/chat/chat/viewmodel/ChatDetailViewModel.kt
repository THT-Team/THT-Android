package tht.feature.chat.chat.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_ui.common.viewmodel.Container
import com.example.compose_ui.common.viewmodel.Store
import com.example.compose_ui.common.viewmodel.intent
import com.example.compose_ui.common.viewmodel.store
import com.tht.tht.domain.chat.usecase.GetChatDetailInformationUseCase
import com.tht.tht.domain.chat.usecase.GetChatHistoryUseCase
import com.tht.tht.domain.token.token.FetchThtUserUuidUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tht.feature.chat.chat.state.ChatDetailSideEffect
import tht.feature.chat.chat.state.ChatDetailState
import tht.feature.chat.mapper.toModel
import javax.inject.Inject

@HiltViewModel
internal class ChatDetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getChatDetailInformationUseCase: GetChatDetailInformationUseCase,
    private val getChatHistoryUseCase: GetChatHistoryUseCase,
    private val fetchThtUserUuidUseCase: FetchThtUserUuidUseCase,
) : ViewModel(), Container<ChatDetailState, ChatDetailSideEffect> {
    override val store: Store<ChatDetailState, ChatDetailSideEffect> =
        store(
            initialState = ChatDetailState.ChatList(
                isLoading = true,
                chatDetailInformation = null,
                chatList = emptyList()
            )
        )

    private var _currentText: MutableStateFlow<String> = MutableStateFlow("")
    val currentText = _currentText.asStateFlow()

    fun getChatDetailInformation(roomIdx: Long) {
        viewModelScope.launch {
            val chatDetailInformation = getChatDetailInformationUseCase(roomIdx).getOrNull()
            intent {
                reduce { state ->
                    (state as ChatDetailState.ChatList).copy(
                        isLoading = false,
                        chatDetailInformation = chatDetailInformation?.toModel(),
                    )
                }
            }
        }
    }

    fun getChatHistory(roomIdx: Long, chatIdx: String? = null, size: String = "20") {
        if ((store.state.value as? ChatDetailState.ChatList)?.chatIdx == "-1") return
        viewModelScope.launch {
            val history = getChatHistoryUseCase(
                roomIdx = roomIdx,
                chatIdx = (store.state.value as? ChatDetailState.ChatList)?.chatIdx ?: chatIdx,
                size = size
            ).getOrNull() ?: emptyList()
            Log.d("test", "getChatHistory: ${history}")
            intent {
                reduce { state ->
                    (state as ChatDetailState.ChatList).copy(
                        isLoading = false,
                        chatList = state.chatList.toMutableList().apply {
                            addAll(0, history.map { it.toModel() }.reversed())
                        },
                        chatIdx = if (history.isEmpty()) "-1"
                        else history.map { it.toModel() }.reversed().firstOrNull()?.chatIdx
                    )
                }
            }
        }
    }

    fun getUserUuid() {
        viewModelScope.launch {
            val userUuid = fetchThtUserUuidUseCase().getOrNull()
            intent {
                reduce { state ->
                    (state as ChatDetailState.ChatList).copy(
                        userUuid = userUuid
                    )
                }
            }
        }
    }

    fun updateCurrentText(text: String) {
        _currentText.update { text }
    }

    fun onClickSent(text: String) {}

    fun onClickGallery() {}
}
