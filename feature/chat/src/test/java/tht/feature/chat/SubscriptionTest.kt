package tht.feature.chat

import com.squareup.okhttp.RequestBody
import com.squareup.okhttp.Response
import com.squareup.okhttp.ResponseBody
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import com.squareup.okhttp.ws.WebSocket
import com.squareup.okhttp.ws.WebSocketListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okio.Buffer
import okio.IOException
import org.junit.Assert.assertEquals
import org.junit.Test
import tht.feature.chat.actioncable.Consumer
import tht.feature.chat.actioncable.Subscription
import java.net.URI

private const val TIMEOUT = 10000L

class SubscriptionTest {

    @Test
    fun identifier() {
        val consumer = Consumer(URI("ws://3.34.157.62/websocket-endpoint"))
        val channel = tht.feature.chat.actioncable.Channel("/sub/chat/1")
        val subscription = Subscription(consumer, channel)

        assertEquals("{\"channel\":\"/sub/chat/1\"}", subscription.identifier)
    }


    @OptIn(ObsoleteCoroutinesApi::class)
    @Test(timeout = TIMEOUT)
    fun onConnected() = runBlocking {
        val events = Channel<String>()

        val mockWebServer = MockWebServer()
        val mockResponse = MockResponse().withWebSocketUpgrade(object : DefaultWebSocketListener() {
            private var currentWebSocket: WebSocket? = null

            override fun onOpen(webSocket: WebSocket?, response: Response?) {
                currentWebSocket = webSocket
                // send welcome message
                launch(Unconfined) {
                    currentWebSocket?.sendMessage(RequestBody.create(WebSocket.TEXT, "{\"type\":\"welcome\"}"))
                }
            }

            override fun onMessage(message: ResponseBody?) {
                message?.also {
                    val text = it.source()?.readUtf8()!!
                    if (text.contains("subscribe")) {
                        // accept subscribe command
                        launch(Unconfined) {
                            currentWebSocket?.sendMessage(
                                RequestBody.create(
                                    WebSocket.TEXT,
                                    "{\"identifier\":\"{\\\"channel\\\":\\\"CommentsChannel\\\"}\",\"type\":\"confirm_subscription\"}"
                                )
                            )
                        }
                    }
                }?.close()
            }
        })
        mockWebServer.enqueue(mockResponse)
        mockWebServer.start()
        val channel = tht.feature.chat.actioncable.Channel("CommentsChannel")
        val consumer = Consumer(URI(mockWebServer.url("/").uri().toString()))
        val subscription = consumer.subscriptions.create(channel)

        subscription.onConnected = {
            launch(Unconfined) {
                events.send("onConnected")
            }
        }

        consumer.connect()

        assertEquals("onConnected", events.receive())

        mockWebServer.shutdown()
    }

    private open class DefaultWebSocketListener : WebSocketListener {
        override fun onOpen(webSocket: WebSocket?, response: Response?) {
        }

        override fun onFailure(e: IOException?, response: Response?) {
        }

        override fun onMessage(message: ResponseBody?) {
        }

        override fun onPong(payload: Buffer?) {
        }

        override fun onClose(code: Int, reason: String?) {
        }
    }
}
