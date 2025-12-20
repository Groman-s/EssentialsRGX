package com.goyanov.essentials.main

import com.goyanov.essentials.rtp.CommandRtp
import com.goyanov.essentials.rtp.RandomTeleportViaCommand
import com.goyanov.essentials.tab.SendTabToPlayers
import com.goyanov.essentials.automessages.AutoMessagesTimer
import com.goyanov.essentials.tab.TabUpdateTimer
import com.goyanov.essentials.tpa.CommandTpa
import com.goyanov.essentials.tpa.CommandTpaTabCompleter
import com.goyanov.essentials.tpa.CommandTpaccept
import com.goyanov.essentials.tpa.PlayersTeleportToOthers
import org.bukkit.plugin.java.JavaPlugin

class EssentialsRGX : JavaPlugin() {

    companion object {
        private lateinit var instance : EssentialsRGX
        fun inst() = instance
    }

    override fun onEnable() {
        instance = this

        saveDefaultConfig()

        val pm = server.pluginManager

        if (config.getBoolean("commands.rtp.enabled")) {
            getCommand("rtp")?.setExecutor(CommandRtp())
            pm.registerEvents(RandomTeleportViaCommand(), this)
        }

        if (config.getBoolean("commands.tpa.enabled")) {
            getCommand("tpa")?.setExecutor(CommandTpa())
            getCommand("tpa")?.tabCompleter = CommandTpaTabCompleter()
            getCommand("tpaccept")?.setExecutor(CommandTpaccept())
            getCommand("tpaccept")?.tabCompleter = CommandTpaTabCompleter()
            pm.registerEvents(PlayersTeleportToOthers(), this)
        }

        if (config.getBoolean("auto-messages.enabled")) {
            val autoMessagesInterval = config.getLong("auto-messages.interval-seconds") * 20L
            AutoMessagesTimer().runTaskTimer(this, autoMessagesInterval, autoMessagesInterval)
        }

        if (config.getBoolean("tab.enabled")) {
            val papiEnabled = pm.getPlugin("PlaceholderAPI") != null
            pm.registerEvents(SendTabToPlayers(papiEnabled), this)
            TabUpdateTimer(papiEnabled).runTaskTimer(this, 1L, 1L)
        }
    }
}