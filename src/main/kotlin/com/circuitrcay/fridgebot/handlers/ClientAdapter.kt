package com.circuitrcay.fridgebot.handlers

import me.devoxin.flight.api.CommandWrapper
import me.devoxin.flight.api.Context
import me.devoxin.flight.exceptions.BadArgument
import me.devoxin.flight.models.CommandClientAdapter
import net.dv8tion.jda.api.Permission
import org.slf4j.LoggerFactory

class ClientAdapter : CommandClientAdapter {
    private val LOG = LoggerFactory.getLogger(this::class.java)

    override fun onBadArgument(ctx: Context, command: CommandWrapper, error: BadArgument) {
        ctx.send("Wrong argument on command `${command.name}`. You provided `${error.providedArgument}")
    }

    override fun onBotMissingPermissions(ctx: Context, command: CommandWrapper, permissions: List<Permission>) {
        ctx.send("The bot is missing permissions, which are as follows: ${permissions.joinToString(", ")}")
    }

    override fun onCommandError(ctx: Context, command: CommandWrapper, error: Throwable) {
        ctx.send("Command broke, go ping <@255114091360681986>. Error: \n```\n${error.message}```")
    }

    override fun onCommandPostInvoke(ctx: Context, command: CommandWrapper, failed: Boolean) {
        LOG.info("Command ${command.name} has been invoked.")
    }

    override fun onCommandPreInvoke(ctx: Context, command: CommandWrapper): Boolean {
        return !command.properties.description.contains("(\\/[dD]isabled\\/)".toRegex())
    }

    override fun onParseError(ctx: Context, command: CommandWrapper, error: Throwable) {
        LOG.error("The command ${command.name} is unable to be parsed.", error)
    }

    override fun onUserMissingPermissions(ctx: Context, command: CommandWrapper, permissions: List<Permission>) {
        ctx.send("You're missing permissions, which are as follows: ${permissions.joinToString(", ")}")
    }
}