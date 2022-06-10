package ru.playzone.features.games

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.playzone.database.games.GameDto
import ru.playzone.database.games.Games
import ru.playzone.database.tokens.Tokens
import java.util.*

class GamesController(private val call: ApplicationCall) {

    suspend fun fetchAllGames() {
        val token = call.request.headers["X-token"]
        if (!Tokens.contains(token)) {
            call.respond(HttpStatusCode.BadRequest, "Token incorrect")
        }
        call.respond(Games.fetchGames().map { game ->
            AddGameRequest(
                name = game.name,
                backdrop = game.backdrop,
                logo = game.logo,
                description = game.description,
                downloadCount = game.downloadCount,
                version = game.version,
                weight = game.weight,
            )
        })
    }

    suspend fun addGame() {
        val token = call.request.headers["X-token"]
        if (!Tokens.contains(token)) {
            call.respond(HttpStatusCode.BadRequest, "Token incorrect")
        }

        val gameRequest = call.receive<AddGameRequest>()
        Games.insert(
            GameDto(
                gameId = UUID.randomUUID().toString(),
                name = gameRequest.name,
                backdrop = gameRequest.backdrop,
                logo = gameRequest.logo,
                description = gameRequest.description,
                downloadCount = gameRequest.downloadCount,
                version = gameRequest.version,
                weight = gameRequest.weight,
            )
        )
    }

}