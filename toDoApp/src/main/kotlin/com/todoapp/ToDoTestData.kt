package com.todoapp

import com.todoapp.entity.TodoEntity
import com.todoapp.entity.WebSocketMessage
import kotlin.random.Random

object ToDoTestData {

    fun toDoData() = TodoEntity(
        id = Random.nextInt(1, 10000),
        text = "this is THE text",
        completed = false
    )

    fun expectedMessageByWS() = WebSocketMessage(
        type = "new_todo",
        data = TodoEntity(
            id = Random.nextInt(1, 10000),
            text = "this is THE text",
            completed = false
        )
    )
}
