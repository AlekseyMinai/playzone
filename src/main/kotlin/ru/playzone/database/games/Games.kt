package ru.playzone.database.games

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Games : Table("games") {
    private val gameId = Games.varchar("gameId", 100)
    private val name = Games.varchar("name", 100)
    private val backdrop = Games.varchar("backdrop", 50).nullable()
    private val logo = Games.varchar("logo", 50)
    private val description = Games.varchar("description", 500)
    private val downloadCount = Games.integer("download_count")
    private val version = Games.varchar("version", 12)
    private val weight = Games.varchar("weight", 10)

    fun insert(gameDto: GameDto) {
        transaction {
            Games.insert {
                it[gameId] = gameDto.gameId
                it[name] = gameDto.name
                it[backdrop] = gameDto.backdrop
                it[logo] = gameDto.logo
                it[description] = gameDto.description
                it[downloadCount] = gameDto.downloadCount
                it[version] = gameDto.version
                it[weight] = gameDto.weight
            }
        }
    }

    fun fetchGames(): List<GameDto> {
        return try {
            transaction {
                Games.selectAll().toList()
                    .map {
                        GameDto(
                            gameId = it[gameId],
                            name = it[name],
                            backdrop = it[backdrop],
                            logo = it[logo],
                            description = it[description],
                            downloadCount = it[downloadCount],
                            version = it[version],
                            weight = it[weight],
                        )
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

}