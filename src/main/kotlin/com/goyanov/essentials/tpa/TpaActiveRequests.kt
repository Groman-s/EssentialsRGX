package com.goyanov.essentials.tpa

import com.goyanov.essentials.main.EssentialsRGX
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class TpaActiveRequests {

    companion object {
        val activeRequests: HashMap<Player, HashMap<Player, BukkitRunnable>> = HashMap()

        fun get(playerInitiator: Player, playerTarget: Player) : BukkitRunnable? {
            return activeRequests[playerTarget]?.get(playerInitiator)
        }

        fun exists(playerInitiator: Player, playerTarget: Player) : Boolean {
            return get(playerInitiator, playerTarget) != null
        }

        fun remove(playerInitiator: Player, playerTarget: Player) {
            if (activeRequests[playerTarget]?.remove(playerInitiator) != null) {
                if (activeRequests[playerTarget]?.isEmpty() ?: false) {
                    activeRequests.remove(playerTarget)
                }
            }
        }

        fun create(playerInitiator: Player, playerTarget: Player) : Boolean {

            if (exists(playerInitiator, playerTarget)) return false

            val targetIncomingRequests: HashMap<Player, BukkitRunnable>? = activeRequests.computeIfAbsent(playerTarget) { HashMap() }

            val timer = object : BukkitRunnable() {
                override fun run() {
                    remove(playerInitiator = playerInitiator, playerTarget = playerTarget)
                    playerInitiator.sendMessage("§8§l| §cЗапрос на телепортацию к игроку ${playerTarget.name} так и не был принят.")
                    playerTarget.sendMessage("§8§l| §cЗапрос на телепортацию от игрока ${playerInitiator.name} так и не был принят.")
                }
            }
            timer.runTaskLater(EssentialsRGX.inst(), EssentialsRGX.inst().config.getLong("commands.tpa.request-lifetime-seconds") * 20)

            targetIncomingRequests!!.put(playerInitiator, timer)

            return true
        }
    }
}