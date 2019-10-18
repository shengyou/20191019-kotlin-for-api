package io.kraftman.api

import io.kraftman.api.entities.Task
import io.kraftman.api.presenters.TaskResponse
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(ContentNegotiation) {
        jackson {
        }
    }

    Database.connect(
        url = "jdbc:mysql://127.0.0.1:8889/todo_local?useUnicode=true&characterEncoding=utf-8&useSSL=false",
        driver = "com.mysql.jdbc.Driver",
        user = "root",
        password = "root"
    )

    routing {

        get("/api/tasks") {
            val tasks = transaction {
                Task.all().sortedByDescending { it.id }.map {
                    TaskResponse(
                        it.title,
                        it.completed,
                        it.createdAt.toString("YYYY-MM-dd HH:mm:ss"),
                        it.updatedAt.toString("YYYY-MM-dd HH:mm:ss")
                    )
                }
            }

            call.respond(mapOf("tasks" to tasks))
        }
    }

}
