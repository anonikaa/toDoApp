package com.todoapp

import com.todoapp.ToDoManager.Companion.todoList
import com.todoapp.entity.TodoEntity
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

class PostToDoTests {
    @AfterEach
    fun deleteTestData(){
        ToDoManager().deleteAllTestData()
    }

    @Test
    fun postTodoSuccess(){
        ToDoManager().apply {
            postToDo(ToDoTestData.toDoData())
                .assertStatusCode(201)
            val expectedResponse = listOf(TodoEntity(todoList.last().id, "this is THE text", false))
            getToDoList().assertEntity(expectedResponse)
        }
    }

    @Test
    fun postTodoWithTheSameId(){
        ToDoManager().apply {
            postToDo(ToDoTestData.toDoData())
                .assertStatusCode(201)
            postToDo(ToDoTestData.toDoData().apply {
                this.id = todoList.last().id
            }).assertStatusCode(400)
        }
    }

    @Test
    fun postToDoWithEmptyText() {
        ToDoManager().apply {
            postToDo(ToDoTestData.toDoData().apply {
                text = ""
            }).assertStatusCode(201)
            val expectedResponse = listOf(TodoEntity(ToDoManager.todoList.last().id, "", false))
            getToDoList().assertEntity(expectedResponse)
        }
    }

    @Test
    fun postToDoWithNullText() {
        ToDoManager().apply {
            postToDo(ToDoTestData.toDoData().apply {
                text = null
            }).assertStatusCode(400)
        }
    }

    @Test
    fun postTodoNullCompleted(){
        ToDoManager().apply {
            postToDo(ToDoTestData.toDoData().apply {
                completed = null
            }).assertStatusCode(400)
        }
    }
}
