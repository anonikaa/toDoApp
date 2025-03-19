package com.todoapp.endpoints

class TodoAppEndpoints: BaseEndpoint() {

    fun toDo(): String = "$server/todos"
    fun toDoById(id: Int): String = "$server/todos/$id"
    fun wsUrl(): String = "$server/ws"
}