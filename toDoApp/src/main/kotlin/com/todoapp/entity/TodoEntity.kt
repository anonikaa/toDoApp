package com.todoapp.entity

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class TodoEntity @JsonCreator constructor(
    @JsonProperty("id")
    var id: Int?,
    @JsonProperty("text")
    var text: String?,
    @JsonProperty("completed")
    var completed: Boolean?
)
