package com.todoapp

import com.todoapp.entity.TodoEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test


class GetToDoTests {
    @AfterEach
    fun deleteTestData(){
        ToDoManager().deleteAllTestData()
    }

    @Test
    fun getEmptyToDoListSuccess(){
        ToDoManager().apply {
            val actualResponse = getToDoList()
                .entity(Array<TodoEntity>::class)
            assertThat(actualResponse).isEmpty()
        }
    }

    @Test
    fun getNotEmptyToDoListWithoutParamsSuccess(){
        ToDoManager().apply {
            postToDo(ToDoTestData.toDoData()).assertStatusCode(201)
            val expectedResponse = listOf(TodoEntity(ToDoManager.todoList.last().id, "this is THE text", false))
            getToDoList().assertEntity(expectedResponse)
        }
    }

    @Test
    fun getToDoListWithOffset(){
        ToDoManager().apply {
            postToDo(ToDoTestData.toDoData()).assertStatusCode(201)
            postToDo(ToDoTestData.toDoData().apply {
                text = "new text"
            }).assertStatusCode(201)
            val expectedResponse = listOf(TodoEntity(ToDoManager.todoList.last().id, "new text", false))
            getToDoList(offset = 1).assertEntity(expectedResponse)
        }
    }
    @Test
    fun getToDoListWithLimit(){
        ToDoManager().apply {
            postToDo(ToDoTestData.toDoData().apply {
                id = 1
                completed = true
                text = "limit text"
            }).assertStatusCode(201)
            postToDo(ToDoTestData.toDoData().apply {
                completed = true
                text = "TEXT"
            }).assertStatusCode(201)
            val expectedResponse = listOf(TodoEntity(1, "limit text", true))
            getToDoList(limit = 1).assertEntity(expectedResponse)
        }
    }

    @Test
    fun getToDoListWithLimitAndOffset(){
        ToDoManager().apply {
            postToDo(ToDoTestData.toDoData()).assertStatusCode(201)
            postToDo(ToDoTestData.toDoData().apply {
                id = 1
                text = "limit and offset text"
            }).assertStatusCode(201)
            postToDo(ToDoTestData.toDoData()).assertStatusCode(201)
            println("TODO LIST ${ToDoManager.todoList}")
            val expectedResponse = listOf(TodoEntity(1, "limit and offset text", false))
            getToDoList(offset = 1, limit = 1).assertEntity(expectedResponse)
        }
    }

}
