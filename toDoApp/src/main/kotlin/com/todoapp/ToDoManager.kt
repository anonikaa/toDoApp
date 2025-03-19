package com.todoapp

import com.todoapp.constants.AUTH_LOGIN_PASSWORD
import com.todoapp.constants.AUTH_NOT_AUTHORIZED_LOGIN_PASSWORD
import com.todoapp.endpoints.TodoAppEndpoints
import com.todoapp.entity.TodoEntity

class ToDoManager {
    companion object {
        val todoList = mutableListOf<TodoEntity>()
        private val createdIds = mutableListOf<Int>()
    }

    fun getToDoList(offset: Int? = null, limit: Int? = null): ApiResponse {
        return Requests().get(
            TodoAppEndpoints().toDo(),
            queryParams = mapOf("offset" to offset, "limit" to limit)
        )
    }

    fun postToDo(
        body: TodoEntity
    ) = Requests().post(TodoAppEndpoints().toDo(), json = body).apply {
        if (this.statusCode == 201){
            todoList.add(body)
        }
    }

    fun deleteToDo(
        id: Int
    ): ApiResponse {
        return (Requests().delete(TodoAppEndpoints().toDoById(id), AUTH_LOGIN_PASSWORD, AUTH_LOGIN_PASSWORD))
    }

    fun deleteToDoNotAuthorized(
        id: Int
    ): ApiResponse {
        return (Requests().delete(TodoAppEndpoints().toDoById(id), AUTH_NOT_AUTHORIZED_LOGIN_PASSWORD, AUTH_NOT_AUTHORIZED_LOGIN_PASSWORD))
    }

    private fun deleteAllToDos() {
        todoList.forEach {
            deleteToDo(it.id!!)
        }
    }

    fun deleteAllTestData() {
        createdIds.clear()
        createdIds.addAll(
            getToDoList().entity(Array<TodoEntity>::class).map { it.id!! }
        )
        createdIds.forEach { deleteToDo(it) }
        createdIds.clear()
    }

    fun putToDo(
        id: Int,
        body: TodoEntity
    ): ApiResponse {
       return Requests().put(TodoAppEndpoints().toDoById(id), json = body)
    }

}
