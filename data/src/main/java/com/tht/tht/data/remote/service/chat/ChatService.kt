package com.tht.tht.data.remote.service.chat

import com.tht.tht.data.constant.THTApiConstant
import com.tht.tht.data.remote.response.base.ThtResponse
import com.tht.tht.data.remote.response.chat.ChatDetailInformationResponse
import com.tht.tht.data.remote.response.chat.ChatHistoryResponse
import com.tht.tht.data.remote.response.chat.ChatListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ChatService {
    @GET(THTApiConstant.Chat.CHAT_LIST)
    suspend fun getChatList(): ThtResponse<List<ChatListResponse>>

    @GET(THTApiConstant.Chat.CHAT_DETAIL_INFORMATION)
    suspend fun getChatDetailInformation(
        @Path(value = "chat-room-idx") roomIdx: Long
    ): ThtResponse<ChatDetailInformationResponse>

    @GET(THTApiConstant.Chat.CHAT_DETAIL_HISTORY)
    suspend fun getChatHistory(
        @Query(value = "roomNo") roomIdx: String,
        @Query(value = "chatIdx") chatIdx:String?,
        @Query(value = "size") size: String,
    ): ThtResponse<List<ChatHistoryResponse>>
}
