package tht.feature.chat.chat.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_ui.common.viewmodel.Container
import com.example.compose_ui.common.viewmodel.Store
import com.example.compose_ui.common.viewmodel.store
import com.tht.tht.data.di.websocket.AllMarketTickerResponseItem
import com.tht.tht.data.di.websocket.BinanceRequest
import com.tht.tht.data.di.websocket.RequestFormatField
import com.tht.tht.data.di.websocket.RequestTicketField
import com.tht.tht.data.di.websocket.RequestTypeField
import com.tht.tht.data.remote.service.chat.SocketService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import tht.feature.chat.chat.state.ChatDetailSideEffect
import tht.feature.chat.chat.state.ChatDetailState
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
internal class ChatDetailViewModel @Inject constructor(
    private val service: SocketService
) : ViewModel(), Container<ChatDetailState, ChatDetailSideEffect> {
    override val store: Store<ChatDetailState, ChatDetailSideEffect> =
        store(
            initialState = ChatDetailState.ChatList(
                isLoading = true,
                chatList = persistentListOf()
            )
        )

//    private var _currentText: MutableStateFlow<String> = MutableStateFlow("")
//    val currentText = _currentText.asStateFlow()
//    fun getChatList() {
//        intent {
//            reduce {
//                ChatDetailState.ChatList(
//                    isLoading = false,
//                    chatList = persistentListOf(
//                        ChatListUiModel(
//                            chatRoomIdx = 1L,
//                            partnerName = "하하",
//                            partnerProfileUrl = "",
//                            currentMessage = "",
//                            messageTime = ""
//                        )
//                    )
//                )
//            }
//        }
//    }
//
//    fun updateCurrentText(text: String) {
//        _currentText.update { text }
//    }
//
//    fun onClickSent(text: String) {}
//
//    fun onClickGallery() {}

    private val _allMarketTickerFlow = MutableStateFlow<List<AllMarketTickerResponseItem>>(emptyList())
    val allMarketTickerFlow: Flow<List<AllMarketTickerResponseItem>> get() = _allMarketTickerFlow

    private val _socketEventFlow = MutableStateFlow<String>("")
    val socketEventFlow: Flow<String> get() = _socketEventFlow

    private val ticket = UUID.randomUUID().toString()
    fun requestAllMarketTicker() = viewModelScope.launch {
        // upbit socket request
        service.requestUpbit(
            listOf(
                RequestTicketField(ticket = ticket),
                RequestTypeField(
                    type = "ticker",
                    codes = listOf("KRW-BTC", "KRW-ETH", "KRW-XRP", "KRW-DOGE")
                ),
                RequestFormatField()
            )
        )
    }

    fun requestCancelAllMarketTicker() {
        // binance socket request unsubscribe
        service.request(
            request = BinanceRequest(
                method = "UNSUBSCRIBE",
                params = listOf("!ticker@arr")
            )
        )
    }

    fun observeAllMarket() {
        service.collectUpbitTicker().onEach {
            Log.d("Thunder", "${it.code} || ${it.trade_price}")
        }.launchIn(viewModelScope)
    }
}
