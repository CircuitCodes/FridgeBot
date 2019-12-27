package com.circuitrcay.fridgebot.entities

data class ConfigToml(
    val discord: Discord = Discord(),
    val irc: IRC = IRC(),
    val git: Git = Git()
)

data class Discord(
    val token: String = "",
    val prefixes: List<String> = listOf(),
    val discordBridgeChannel: Long = 0L,
    val ownerId: Long = 0L
)

data class IRC(
    val host: String = "",
    val autoJoinChannel: String = "",
    val nick: String = ""
)

data class Git(
    val username: String = "",
    val password: String = ""
)