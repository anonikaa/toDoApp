package com.todoapp.webSocket

import com.todoapp.entity.WebSocketMessage
import org.junit.jupiter.api.Assertions.assertEquals

object WebSocketAssertions {
    inline fun <reified T> assertWebSocketEntity(receivedMessage: WebSocketMessage?, expected: T) {
        assertEquals(expected, receivedMessage)
    }
}
