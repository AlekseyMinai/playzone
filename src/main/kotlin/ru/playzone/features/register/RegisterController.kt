package ru.playzone.features.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.playzone.database.tokens.TokenDto
import ru.playzone.database.tokens.Tokens
import ru.playzone.database.users.User
import ru.playzone.database.users.UserDto
import ru.playzone.utils.isValidEmail
import java.util.*

class RegisterController(private val call: ApplicationCall) {

    suspend fun registerNewUser() {
        val registerReceiveRemote = call.receive<RegisterReceiveRemote>()
        if (!registerReceiveRemote.email.isValidEmail()) {
            call.respond(HttpStatusCode.BadRequest, "Email is not valid")
        }

        val userDto = User.fetchUser(registerReceiveRemote.login)

        if (userDto != null) {
            return call.respond(
                HttpStatusCode.Conflict,
                "User already exist"
            )
        }

        val token = UUID.randomUUID().toString()
        User.insert(
            UserDto(
                login = registerReceiveRemote.login,
                password = registerReceiveRemote.password,
                email = registerReceiveRemote.email,
                username = ""
            )
        )

        Tokens.insert(
            TokenDto(
                id = UUID.randomUUID().toString(),
                login = registerReceiveRemote.login,
                token = token
            )
        )

        call.respond(RegisterResponseRemote(token = token))
    }

}