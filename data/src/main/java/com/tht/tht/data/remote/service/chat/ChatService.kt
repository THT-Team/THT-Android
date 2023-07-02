package com.tht.tht.data.remote.service.chat

import com.tht.tht.data.constant.THTApiConstant
import com.tht.tht.data.remote.response.base.ThtResponse
import com.tht.tht.data.remote.response.chat.ChatListResponse
import retrofit2.http.GET

interface ChatService {
    @GET(THTApiConstant.Chat.CHATLIST)
    suspend fun getChatList(): ThtResponse<List<ChatListResponse>>
}
