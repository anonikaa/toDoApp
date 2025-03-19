package com.todoapp.webSocket

import com.todoapp.endpoints.TodoAppEndpoints
import com.todoapp.entity.WebSocketMessage
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class WebSocketClient {
    private val client = OkHttpClient()
    private val request = Request.Builder()
        .url(TodoAppEndpoints().wsUrl())
        .build()

    private var onMessageCallback: ((WebSocketMessage?) -> Unit)? = null
    var lastMessage: WebSocketMessage? = null

    fun setOnMessageCallback(callback: (WebSocketMessage?) -> Unit) {
        this.onMessageCallback = callback
    }

    fun connect() {
        client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
                println("WebSocket established")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                lastMessage = WebSocketMessageHandler().handleMessage(text)
                onMessageCallback?.invoke(lastMessage)
                println("New request is: $text")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                println("🚪 WebSocket is closing: $code $reason")
                webSocket.close(1000, null)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
                println("Error WebSocket: ${t.message}")
            }
        })
    }

    fun waitForMessage(timeout: Long = 10, timeUnit: TimeUnit = TimeUnit.SECONDS): WebSocketMessage? {
        val latch = CountDownLatch(1)
        var receivedMessage: WebSocketMessage? = null

        setOnMessageCallback { message ->
            receivedMessage = message
            latch.countDown()
        }

        if (!latch.await(timeout, timeUnit)) {
            throw AssertionError("NO DATA FOR $timeout SEC")
        }

        return receivedMessage
    }

    fun disconnect() {
        client.dispatcher.executorService.shutdown()
        println("WebSocket is shout down")
    }
}
