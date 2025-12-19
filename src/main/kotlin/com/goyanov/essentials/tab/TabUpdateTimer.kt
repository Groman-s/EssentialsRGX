package com.goyanov.essentials.tab

import com.goyanov.essentials.main.EssentialsRGX
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

class TabUpdateTimer(val papiEnabled: Boolean) : BukkitRunnable() {

    private var headerUpdateInterval = EssentialsRGX.inst().config.getInt("tab.update-ticks.header")
    private var footerUpdateInterval = EssentialsRGX.inst().config.getInt("tab.update-ticks.footer")
    private var playerNamesUpdateInterval = EssentialsRGX.inst().config.getInt("tab.update-ticks.player-names")

    private var headerUpdateTick = 0
    private var footerUpdateTick = 0
    private var playerNameUpdateTick = 0

    override fun run() {

        if (headerUpdateTick++ % headerUpdateInterval == 0) {
            Bukkit.getOnlinePlayers().forEach { player -> sendCustomHeaders(player, papiEnabled) }
        }

        if (footerUpdateTick++ % footerUpdateInterval == 0) {
            Bukkit.getOnlinePlayers().forEach { player -> sendCustomFooters(player, papiEnabled) }
        }

        if (playerNameUpdateTick++ % playerNamesUpdateInterval == 0) {
            Bukkit.getOnlinePlayers().forEach { player -> updatePlayerTabName(player, papiEnabled) }
        }
    }
}