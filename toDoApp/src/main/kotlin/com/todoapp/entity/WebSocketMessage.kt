package com.todoapp.entity


data class WebSocketMessage(
    val type: String,
    val data: TodoEntity
)