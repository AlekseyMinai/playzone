package ru.playzone.features.games

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureGamesRouting() {

    routing {
        get("/games/fetch") {
            val gamesController = GamesController(call)
            gamesController.fetchAllGames()
        }

        post("/games/add") {
            val gamesController = GamesController(call)
            gamesController.addGame()
        }
    }
}
