package com.circuitrcay.fridgebot.handlers

import com.circuitrcay.fridgebot.Fridge
import com.circuitrcay.fridgebot.utils.sanitiseFromIrc
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class DiscordBridgeHandler : ListenerAdapter() {
    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        if(event.channel.idLong != Fridge.config.discord.discordBridgeChannel) return
        if(event.author.id == event.jda.selfUser.id) return

        val user = event.author

        val format = "[${user.name}#${user.discriminator}] ${event.message.contentStripped}"

        val ircChannel = Fridge.config.irc.autoJoinChannel

        Fridge.irc.send().message(ircChannel, format)
    }
}