package com.tht.tht.data.remote.service.chat

import com.jeremy.thunder.ws.Receive
import com.jeremy.thunder.ws.Send
import com.tht.tht.data.di.websocket.AllMarketResponse
import com.tht.tht.data.di.websocket.AllMarketTickerResponse
import com.tht.tht.data.di.websocket.BinanceRequest
import com.tht.tht.data.di.websocket.TickerResponse
import com.tht.tht.data.di.websocket.UpbitRequest
import com.tht.tht.data.di.websocket.UpbitTickerResponse
import kotlinx.coroutines.flow.Flow

interface SocketService {

//    @StompSubscribe
//    fun request(request: Any)
//
//    @StompSend
//    fun send(request: Any)
//
//    @Receive
//    fun response(): Flow<*>

    @Send
    fun request(request: BinanceRequest)

    @Send
    fun requestUpbit(request: List<UpbitRequest>)

    @Receive
    fun collectUpbitTicker(): Flow<UpbitTickerResponse>


    @Receive
    fun observeTicker(): Flow<TickerResponse>

    @Receive
    fun observeAllMarkets(): Flow<AllMarketResponse>

    @Receive
    fun observeAllMarketTickers(): Flow<AllMarketTickerResponse>
}
