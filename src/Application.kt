package io.kraftman.api

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    routing {

        get("/") {
            //language=JSON
            call.respondText("{\n  \"result\": true,\n  \"data\": [\n    {\n      \"id\": 1,\n      \"title\": \"Item 1\"\n    },\n    {\n      \"id\": 2,\n      \"title\": \"Item 2\"\n    }\n  ]\n  \n}", ContentType.Application.Json, HttpStatusCode.OK)
        }

    }

}

