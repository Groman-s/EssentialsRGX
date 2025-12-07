package com.goyanov.essentials.rtp

import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class RandomTeleportEvent(val player: Player, val teleportLocation: Location, val currentTry: Int = 1) : Event(), Cancellable {

    private var cancelled: Boolean = false
    override fun isCancelled() = cancelled
    override fun setCancelled(cancelled: Boolean) { this.cancelled = cancelled }

    override fun getHandlers() = HANDLERS
    companion object {
        private val HANDLERS = HandlerList()
        @JvmStatic fun getHandlerList() = HANDLERS
    }

    var badPosition = false
    fun markAsBadPosition() { badPosition = true }
}