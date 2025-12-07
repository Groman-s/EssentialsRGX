package com.goyanov.essentials.rtp

import com.goyanov.essentials.main.EssentialsRGX
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class RandomTeleportViaCommand : Listener {

    companion object {
        private val config = EssentialsRGX.Companion.inst().config
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun teleportOrAnotherTry(e: RandomTeleportEvent) {
        if (e.badPosition) {
            if (e.currentTry >= config.getInt("commands.rtp.max-tries")) {
                e.player.sendMessage("§8§l| §cНе получилось найти подходящую локацию для случайной телепортации. Попробуй снова или обратись к администратору.")
            } else {
                val loc = RandomPlaceSearcher.findRandomLocation(e.player.world)
                Bukkit.getPluginManager().callEvent(RandomTeleportEvent(e.player, loc, e.currentTry + 1))
            }
        } else {
            e.player.teleport(e.teleportLocation)
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun badPositionIfRegexBlacklist(e: RandomTeleportEvent) {
        if (e.badPosition) return

        val blockRegexBlackList = config.getStringList("commands.rtp.unsafe-blocks-regex")

        val teleportLoc = e.teleportLocation
        val highestLoc: Location = teleportLoc.clone().add(0.0, -1.0, 0.0)

        val locForbidden = fun (loc: Location) = blockRegexBlackList.any { loc.block.type.name.matches(Regex(it)) }

        if (locForbidden(highestLoc) || locForbidden(teleportLoc)) {
            e.markAsBadPosition()
            return
        }
    }
}