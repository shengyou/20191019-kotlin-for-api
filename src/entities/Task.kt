package io.kraftman.api.entities

import io.kraftman.api.tables.Tasks
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class Task(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Task>(Tasks)

    var title by Tasks.title
    var completed by Tasks.completed
    var createdAt by Tasks.createdAt
    var updatedAt by Tasks.updatedAt
}