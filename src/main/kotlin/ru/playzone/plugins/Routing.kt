package ru.playzone.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.playzone.features.login.LoginResponseRemote

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respond(LoginResponseRemote("asdf"))
        }
    }
}
