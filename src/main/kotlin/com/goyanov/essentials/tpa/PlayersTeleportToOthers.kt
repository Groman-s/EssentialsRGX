package com.goyanov.essentials.tpa

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class PlayersTeleportToOthers : Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun teleportPlayerToAnotherPlayer(e: PlayerTpaAcceptEvent) {
        TpaActiveRequests.get(playerInitiator = e.tpaInitiator, playerTarget = e.tpaTarget)?.cancel()
        TpaActiveRequests.remove(playerInitiator = e.tpaInitiator, playerTarget = e.tpaTarget)
        e.tpaTarget.sendMessage("§8§l| §aТы принял запрос ${e.tpaInitiator.name} на телепортацию к тебе.")
        e.tpaInitiator.sendMessage("§8§l| §a${e.tpaTarget.name} принял твой запрос на телепортацию.")
        e.tpaInitiator.teleport(e.tpaTarget)
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun showTpaRequest(e: PlayerTpaSendEvent) {
        TpaActiveRequests.create(e.tpaInitiator, e.tpaTarget)
        e.tpaInitiator.sendMessage("§8§l| §aЗапрос на телепортацию отправлен игроку ${e.tpaTarget.name}.")
        e.tpaTarget.sendMessage("§8§l| §a${e.tpaInitiator.name} хочет к тебе телепортироваться. Введи /tpaccept ${e.tpaInitiator.name}, чтобы принять запрос.")
    }
}