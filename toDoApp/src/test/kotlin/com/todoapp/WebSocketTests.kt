package com.todoapp

import com.todoapp.ToDoManager.Companion.todoList
import com.todoapp.entity.TodoEntity
import com.todoapp.entity.WebSocketMessage
import com.todoapp.webSocket.WebSocketAssertions.assertWebSocketEntity
import com.todoapp.webSocket.WebSocketClient
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class WebSocketTests {
    companion object {
        val client = WebSocketClient()

        @BeforeAll
        @JvmStatic
        fun connectWS() {
            client.connect()
        }

        @JvmStatic
        @AfterAll
        fun disconnectWS(): Unit {
            client.disconnect()
        }
    }
    @AfterEach
    fun deleteTestData(){
        ToDoManager().deleteAllTestData()
    }

    @Test
    fun getMessageByWs() {
        ToDoManager().apply {
            postToDo(ToDoTestData.toDoData().apply {
                text = "WS text"
            })
        }
        val receivedMessage = client.waitForMessage()
        assertNotNull(receivedMessage)
    }

    @Test
    fun checkMessageByWs(){
        ToDoManager().apply {
            postToDo(ToDoTestData.toDoData())
        }
        val receivedMessage = client.waitForMessage()
        assertWebSocketEntity(receivedMessage, ToDoTestData.expectedMessageByWS().apply {
            data.id = todoList.last().id
        })
    }
}
