package com.todoapp.webSocket

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.todoapp.entity.WebSocketMessage

class WebSocketMessageHandler {
    private val objectMapper = jacksonObjectMapper()

    fun handleMessage(messageJson: String): WebSocketMessage? {
        try {
            val message: WebSocketMessage = objectMapper.readValue(messageJson)

            when (message.type) {
                "new_todo" -> {
                    println("New To Do: ${message.data.id}, Text: \"${message.data.text}\", Completed: ${message.data.completed}")
                    return message
                }

                else -> {
                    println("Unknown type of message: ${message.type}")
                    return message
                }
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
            return null
        }
    }
}


