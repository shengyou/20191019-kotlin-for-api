package io.kraftman.api

import io.kraftman.api.entities.Task
import io.kraftman.api.presenters.TaskResponse
import io.kraftman.api.requests.TaskRequest
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

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

        post("/api/tasks") {
            val createTask = call.receive<TaskRequest>()
            transaction {
                Task.new {
                    title = createTask.title
                    completed = false
                    createdAt = DateTime.now()
                    updatedAt = DateTime.now()
                }
            }

            call.respond(HttpStatusCode.Created)
        }

        put("/api/tasks/{id}") {
            val id = call.parameters["id"]?.toInt()!!
            transaction {
                val task = Task.findById(id)!!
                task.completed = true
            }

            call.respond(HttpStatusCode.OK)
        }

        delete("/api/tasks/{id}") {
            val id = call.parameters["id"]?.toInt()!!
            transaction {
                val task = Task.findById(id)!!
                task.delete()
            }

            call.respond(HttpStatusCode.Gone)
        }
    }

}
