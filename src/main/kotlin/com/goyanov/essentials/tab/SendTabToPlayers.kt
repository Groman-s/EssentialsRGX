package com.goyanov.essentials.tab

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class SendTabToPlayers(val papiEnabled: Boolean) : Listener {

    @EventHandler
    fun sendOnJoin(e: PlayerJoinEvent) {
        sendCustomHeaders(player = e.player, papiEnabled = papiEnabled)
        sendCustomFooters(player = e.player, papiEnabled = papiEnabled)
        updatePlayerTabName(player = e.player, papiEnabled = papiEnabled)
    }
}