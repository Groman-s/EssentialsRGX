package com.goyanov.essentials.tpa

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class PlayerTpaSendEvent(val tpaInitiator: Player, val tpaTarget: Player) : Event(), Cancellable {

    private var cancelled: Boolean = false
    override fun isCancelled() = cancelled
    override fun setCancelled(cancelled: Boolean) { this.cancelled = cancelled }

    override fun getHandlers() = HANDLERS
    companion object {
        private val HANDLERS = HandlerList()
        @JvmStatic fun getHandlerList() = HANDLERS
    }
}