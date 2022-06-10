package ru.playzone.features.games

import kotlinx.serialization.Serializable

@Serializable
class AddGameRequest(
    val name: String,
    val backdrop: String?,
    val logo: String,
    val description: String,
    val downloadCount: Int,
    val version: String,
    val weight: String,
)