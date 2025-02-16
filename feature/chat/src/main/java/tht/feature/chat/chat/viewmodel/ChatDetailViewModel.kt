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
import com.tht.tht.domain.setting.usecase.FetchMyPageUserInfoUseCase
import com.tht.tht.domain.token.token.FetchThtAccessTokenUseCase
import com.tht.tht.domain.token.token.FetchThtUserUuidUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.serializer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.stomp.StompSession
import org.hildan.krossbow.stomp.conversions.kxserialization.StompSessionWithKxSerialization
import org.hildan.krossbow.stomp.conversions.kxserialization.json.withJsonConversions
import org.hildan.krossbow.stomp.use
import org.hildan.krossbow.websocket.okhttp.OkHttpWebSocketClient
//import kotlinx.serialization.Serializable
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import org.hildan.krossbow.stomp.StompClient
//import org.hildan.krossbow.stomp.StompSession
//import org.hildan.krossbow.stomp.conversions.kxserialization.StompSessionWithKxSerialization
//import org.hildan.krossbow.stomp.conversions.kxserialization.convertAndSend
//import org.hildan.krossbow.stomp.conversions.kxserialization.json.withJsonConversions
//import org.hildan.krossbow.stomp.conversions.kxserialization.subscribe
//import org.hildan.krossbow.stomp.use
//import org.hildan.krossbow.websocket.okhttp.OkHttpWebSocketClient
import tht.feature.chat.chat.state.ChatDetailSideEffect
import tht.feature.chat.chat.state.ChatDetailState
import tht.feature.chat.mapper.toModel
import tht.feature.setting.uimodel.mapper.toUiModel
import javax.inject.Inject

@HiltViewModel
internal class ChatDetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getChatDetailInformationUseCase: GetChatDetailInformationUseCase,
    private val getChatHistoryUseCase: GetChatHistoryUseCase,
    private val fetchThtUserUuidUseCase: FetchThtUserUuidUseCase,
    private val fetchThtAccessTokenUseCase: FetchThtAccessTokenUseCase,
    private val fetchMyPageUserInfoUseCase: FetchMyPageUserInfoUseCase
) : ViewModel(), Container<ChatDetailState, ChatDetailSideEffect> {
    override val store: Store<ChatDetailState, ChatDetailSideEffect> =
        store(
            initialState = ChatDetailState.ChatList(
                isLoading = true,
                chatDetailInformation = null,
                chatList = emptyList()
            )
        )

    /**
     * connect request url : ws://3.34.157.62/websocket-endpoint
     * subscribe url : /sub/chat/{room-number}
     * publish url : /pub/chat/{room-number}
     * stomp REQUEST body
     * {
     *     "sender" : "user name - 1",
     *     "senderUuid" : "user-uuid",
     *     "imgUrl" : "user-profile-url",
     *     "msg" : "i am very hansome guyguy ! ! ! !!"    //대화 메세지
     * }
     * stomp RESPONSE body
     * {
     *   "chatIdx": "65698d494ad0c35a716aa8a1",
     *   "sender": "user name - 1",
     *   "senderUuid": "user-uuid",
     *   "msg": "i am very hansome guyguy ! ! ! !!",
     *   "imgUrl": "user-profile-url",
     *   "dateTime": "2023-12-01T16:37:45.39331"
     * }
     */
    private lateinit var stompSession: StompSession
    private lateinit var jsonStompSession: StompSessionWithKxSerialization
    private lateinit var newChatMessage: Flow<String>


    fun connectionWebSocket(roomIdx: Long) {
        viewModelScope.launch {
            (store.state as? ChatDetailState.ChatList)?.let { user ->
                fetchThtAccessTokenUseCase.invoke().getOrNull()?.let { token ->

                    val okHttpClient = OkHttpClient.Builder()
                        .addInterceptor(
                            HttpLoggingInterceptor().apply {
                                level = HttpLoggingInterceptor.Level.BODY
                            }
                        )
                        .build()

                    val client = StompClient(
                        OkHttpWebSocketClient(okHttpClient)
                    )

                    stompSession = client.connect(
                        "ws://3.34.157.62/websocket-endpoint",
                        customStompConnectHeaders = mapOf("Authorization" to token)
                    )
                    jsonStompSession = stompSession.withJsonConversions()
                    Log.d("chatting", "connectionWebSocket: ${jsonStompSession}")
//                    jsonStompSession.use { s ->
//                        s.subscribe(
//                            "/sub/chat/${roomIdx}",
//                            ChatMessage.serializer()
//                        ).collect {
//                            Log.d("chatting", "newChatMessage: $it")
//                        }
                    }
                }
            }
        }

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

        fun getChatHistory(roomIdx: Long, chatIdx: String? = null, size: String = "100") {
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
                fetchMyPageUserInfoUseCase()
                    .onSuccess { userInformation ->
                        intent {
                            reduce { state ->
                                (state as ChatDetailState.ChatList).copy(
                                    userUuid = userUuid,
                                    userInformation = userInformation.toUiModel()
                                )
                            }
                        }
                    }
            }
        }

        fun updateCurrentText(text: String) {
            _currentText.update { text }
        }

        fun onClickSent(roomIdx: Long) {
            if (_currentText.value.isEmpty() || _currentText.value.isBlank()) return
            viewModelScope.launch {
                (store.state.value as? ChatDetailState.ChatList)?.let { state ->
                    state.userInformation?.let { user ->
//                    jsonStompSession.use { s ->
//                        s.convertAndSend(
//                            "/pub/chat/${roomIdx}", ChatMessage(
//                                user.username,
//                                user.userUuid,
//                                user.userProfilePhotos.firstOrNull()?.url ?: "",
//                                msg = currentText.value,
//                            ), ChatMessage.serializer()
//                        )
//                    }
                    }
                }
            }
        }

        fun onClickGallery() {}
    }

    //@Serializable
    data class ChatMessage(
        val sender: String,
        val senderUuid: String,
        val imgUrl: String,
        val msg: String
    )
