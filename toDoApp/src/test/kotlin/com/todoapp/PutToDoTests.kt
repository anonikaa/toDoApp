package com.todoapp

import com.todoapp.ToDoManager.Companion.todoList
import com.todoapp.entity.TodoEntity
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

class PutToDoTests {
    @AfterEach
    fun deleteTestData(){
        ToDoManager().deleteAllTestData()
    }

    @Test
    fun putToDoSuccess(){
        ToDoManager().apply {
            postToDo(ToDoTestData.toDoData()).assertStatusCode(201)

            val expected = TodoEntity(id = todoList.last().id, text = "new_text", completed = false)
            putToDo(id = todoList.last().id!!, expected)
                .assertStatusCode(200)
            getToDoList().assertEntity(listOf(expected))
        }
    }

    @Test
    fun putToDoChangeCompleted(){
        ToDoManager().apply {
            postToDo(ToDoTestData.toDoData()).assertStatusCode(201)

            val expected = TodoEntity(id = todoList.last().id, text = "this is THE text", completed = true)
            putToDo(id = todoList.last().id!!, expected)
                .assertStatusCode(200)
            getToDoList().assertEntity(listOf(expected))
        }
    }

    @Test
    fun putToDoChangeTextAndCompleted(){
        ToDoManager().apply {
            postToDo(ToDoTestData.toDoData()).assertStatusCode(201)

            val expected = TodoEntity(id = todoList.last().id, text = "new_text", completed = true)
            putToDo(id = todoList.last().id!!, expected)
                .assertStatusCode(200)
            getToDoList().assertEntity(listOf(expected))
        }
    }

    @Test
    fun putToDoChangeId(){
        ToDoManager().apply {
            postToDo(ToDoTestData.toDoData()).assertStatusCode(201)

            val expected = TodoEntity(id = 101, text = "new_text", completed = true)
            putToDo(id = todoList.last().id!!, expected)
                .assertStatusCode(200)
            getToDoList().assertEntity(listOf(expected))
        }
    }

    @Test
    fun putToDoToWrongId(){
        ToDoManager().apply {
            postToDo(ToDoTestData.toDoData()).assertStatusCode(201)

            val expected = TodoEntity(id = todoList.last().id!!, text = "TEXT", completed = true)
            putToDo(id = 101, expected)
                .assertStatusCode(404)
        }
    }
}