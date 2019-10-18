package io.kraftman.api.tables

import org.jetbrains.exposed.dao.IntIdTable

object Tasks: IntIdTable() {
    val title = varchar("title", 255)
    val completed = bool("completed")
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
}