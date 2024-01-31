package com.tht.tht.data.di.websocket

import android.content.Context
import com.jeremy.thunder.Thunder
import com.jeremy.thunder.event.converter.ConverterType
import com.jeremy.thunder.makeWebSocketCore
import com.jeremy.thunder.stomp.compiler.StompEventMapper
import com.jeremy.thunder.stomp.internal.StompStateManager
import com.jeremy.thunder.thunder
import com.tht.tht.data.remote.service.chat.SocketService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WebSocketModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .pingInterval(
                10,
                TimeUnit.SECONDS
            ) // If there are no events for a minute, we need to put in some code for ping pong to output a socket connection error from okhttp.
            .build()
    }

    @Provides
    @Singleton
    fun provideSocketService(
        okHttpClient: OkHttpClient,
        @ApplicationContext context: Context
    ): SocketService {
        return Thunder.Builder()
            .setWebSocketFactory(okHttpClient.makeWebSocketCore("ws://3.34.157.62/websocket-endpoint"))
            .setApplicationContext(context) // must required
            .setConverterType(ConverterType.Serialization)
            .setStateManager(StompStateManager.Factory()) // must required
            .setEventMapper(StompEventMapper.Factory()) // must required
            .build()
            .create()
    }
}
