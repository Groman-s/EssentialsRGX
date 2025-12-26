package com.goyanov.essentials.tab

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class SendTabToPlayers private constructor(var papiEnabled: Boolean) : Listener {

    companion object {
        private var instance: SendTabToPlayers? = null

        fun getInstance(): SendTabToPlayers {
            instance ?: run { instance = SendTabToPlayers(false) }
            return instance!!
        }
    }

    @EventHandler
    fun sendOnJoin(e: PlayerJoinEvent) {
        updateFullTab(player = e.player, papiEnabled = papiEnabled)
    }
}