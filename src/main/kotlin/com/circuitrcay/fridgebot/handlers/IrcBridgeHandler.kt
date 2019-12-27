package com.circuitrcay.fridgebot.handlers

import com.circuitrcay.fridgebot.Fridge
import com.circuitrcay.fridgebot.utils.matrixReplace
import com.circuitrcay.fridgebot.utils.sanitiseFromIrc
import org.pircbotx.hooks.ListenerAdapter
import org.pircbotx.hooks.events.MessageEvent


class IrcBridgeHandler : ListenerAdapter() {
    override fun onMessage(event: MessageEvent) {
        if(event.channel.name != Fridge.config.irc.autoJoinChannel) return
        val message = event.message.sanitiseFromIrc()
        val author = event.user!!.nick.matrixReplace()

        val format = "**[${author}]** $message"
        val textChannel = Fridge.jda.getTextChannelById(Fridge.config.discord.discordBridgeChannel)

        textChannel!!.sendMessage(format).queue()
    }
}