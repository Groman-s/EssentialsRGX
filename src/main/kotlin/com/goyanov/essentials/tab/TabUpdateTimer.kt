package com.goyanov.essentials.tab

import com.goyanov.essentials.main.EssentialsRGX
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

class TabUpdateTimer private constructor(var papiEnabled: Boolean) : BukkitRunnable() {

    companion object {
        private var instance: TabUpdateTimer? = null

        fun stop() {
            instance?.cancel()
        }

        fun start(papiEnabled: Boolean) {
            instance = TabUpdateTimer(papiEnabled)
            instance!!.runTaskTimer(EssentialsRGX.inst(), 1L, 1L)
        }
    }

    private var headerUpdateInterval = EssentialsRGX.inst().config.getInt("tab.update-ticks.header")
    private var footerUpdateInterval = EssentialsRGX.inst().config.getInt("tab.update-ticks.footer")
    private var playerNamesUpdateInterval = EssentialsRGX.inst().config.getInt("tab.update-ticks.player-names")

    private var currentTick = 0

    override fun run() {

        if (headerUpdateInterval >= 0 && currentTick % headerUpdateInterval == 0) {
            Bukkit.getOnlinePlayers().forEach { player -> sendCustomHeaders(player, papiEnabled) }
        }

        if (footerUpdateInterval >= 0 && currentTick % footerUpdateInterval == 0) {
            Bukkit.getOnlinePlayers().forEach { player -> sendCustomFooters(player, papiEnabled) }
        }

        if (playerNamesUpdateInterval >= 0 && currentTick % playerNamesUpdateInterval == 0) {
            Bukkit.getOnlinePlayers().forEach { player -> updatePlayerTabName(player, papiEnabled) }
        }

        currentTick++
    }
}