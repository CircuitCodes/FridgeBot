package com.circuitrcay.fridgebot

import com.circuitrcay.fridgebot.entities.ConfigToml
import com.circuitrcay.fridgebot.handlers.DiscordBridgeHandler
import com.circuitrcay.fridgebot.handlers.IrcBridgeHandler
import com.google.gson.Gson
import com.moandjiezana.toml.Toml
import com.moandjiezana.toml.TomlWriter
import me.devoxin.flight.api.CommandClient
import me.devoxin.flight.api.CommandClientBuilder
import me.devoxin.flight.internal.CommandRegistry
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import org.pircbotx.Configuration
import org.pircbotx.PircBotX
import org.slf4j.LoggerFactory
import java.nio.file.Paths
import kotlin.system.exitProcess

object Fridge {
    lateinit var handler: CommandClient
    lateinit var config: ConfigToml
    lateinit var jda: JDA
    lateinit var irc: PircBotX
    lateinit var gson: Gson

    private val LOG = LoggerFactory.getLogger(this::class.java)

    val configPath = Paths.get("config.toml")

    @ExperimentalStdlibApi
    @JvmStatic
    fun main(args: Array<String>) {
        gson = Gson()

        if(!configPath.toFile().exists()) {
            LOG.error("The file config.toml doesn't exist. Creating and exiting...")
            val writer = TomlWriter()
            writer.write(ConfigToml(), configPath.toFile())
            LOG.error("Created a config file, edit as needed. Exiting...")
            exitProcess(1)
        }
        config = Toml().read(configPath.toFile()).to(ConfigToml::class.java)

        handler = CommandClientBuilder()
            .setAllowMentionPrefix(true)
            .setOwnerIds(config.discord.ownerId)
            .setPrefixes(config.discord.prefixes)
            .registerDefaultParsers()
            .configureDefaultHelpCommand {
                this.enabled = true
                this.showParameterTypes = true
            }
            .build()

        handler.registerCommands("com.circuitrcay.fridgebot.cogs")


        LOG.info("Registering handler...")

        jda = JDABuilder(config.discord.token)
            .addEventListeners(handler, DiscordBridgeHandler())
            .build()

        val ircConf = Configuration.Builder()
            .setName(config.irc.nick)
            .addServer(config.irc.host)
            .addAutoJoinChannel(config.irc.autoJoinChannel)
            .addListener(IrcBridgeHandler())
            .buildConfiguration()

        irc = PircBotX(ircConf)

        irc.startBot()

        LOG.info("Connected to IRC!")
    }
}