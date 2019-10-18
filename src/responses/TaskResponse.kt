package io.kraftman.api.presenters

data class TaskResponse(
    val title: String,
    val completed: Boolean,
    val created_at: String,
    val updated_at: String
)
