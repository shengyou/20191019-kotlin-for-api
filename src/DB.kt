package io.kraftman.api

import io.kraftman.api.entities.Task
import io.kraftman.api.tables.Tasks
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

fun main() {

    Database.connect(
        url = "jdbc:mysql://127.0.0.1:8889/todo_local?useUnicode=true&characterEncoding=utf-8&useSSL=false",
        driver = "com.mysql.jdbc.Driver",
        user = "root",
        password = "root"
    )

    transaction {
        SchemaUtils.drop(Tasks)
        SchemaUtils.create(Tasks)
    }

    transaction {
        for (i in 1..10) {
            Task.new {
                title = "Task $i"
                completed = listOf(true, false, false, false).shuffled().first()
                createdAt = DateTime.now()
                updatedAt = DateTime.now()
            }
        }
    }

}