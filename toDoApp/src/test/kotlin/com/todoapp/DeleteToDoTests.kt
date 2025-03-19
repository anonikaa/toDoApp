package com.todoapp

import com.todoapp.ToDoManager.Companion.todoList
import com.todoapp.entity.TodoEntity
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

class DeleteToDoTests {
    @AfterEach
    fun deleteTestData(){
        ToDoManager().deleteAllTestData()
    }

    @Test
    fun deleteExistToDoSuccess(){
        ToDoManager().apply {
            postToDo(ToDoTestData.toDoData()).assertStatusCode(201)
            postToDo(ToDoTestData.toDoData()).assertStatusCode(201)

            val id = todoList.last().id!!
            deleteToDo(id).assertStatusCode(204)

            val actualResponse = getToDoList().entity(Array<TodoEntity>::class)
            assertFalse(actualResponse.any { it.id == id })
        }
    }

    @Test
    fun deleteNotExistToDo() {
        ToDoManager().apply {
            deleteToDo(2)
                .assertStatusCode(404)
        }
    }

    @Test
    fun deleteDeletedToDo(){
        ToDoManager().apply {
            postToDo(ToDoTestData.toDoData()).assertStatusCode(201)
            val id = todoList.last().id!!
            deleteToDo(id).assertStatusCode(204)
            deleteToDo(id).assertStatusCode(404)
        }
    }

    @Test
    fun deleteToDoWithoutPermission(){
        ToDoManager().apply {
            postToDo(ToDoTestData.toDoData()).assertStatusCode(201)
            val id = todoList.last().id!!
            deleteToDoNotAuthorized(id)
                .assertStatusCode(401)
        }
    }
}