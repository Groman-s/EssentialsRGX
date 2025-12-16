package com.goyanov.essentials.main

import com.goyanov.essentials.rtp.CommandRtp
import com.goyanov.essentials.rtp.RandomTeleportViaCommand
import com.goyanov.essentials.timers.AutoMessagesTimer
import com.goyanov.essentials.tpa.CommandTpa
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

        if (config.getBoolean("commands.rtp.enabled")) {
            getCommand("rtp")?.setExecutor(CommandRtp())
            server.pluginManager.registerEvents(RandomTeleportViaCommand(), this)
        }

        if (config.getBoolean("commands.tpa.enabled")) {
            getCommand("tpa")?.setExecutor(CommandTpa())
            getCommand("tpaccept")?.setExecutor(CommandTpaccept())
            server.pluginManager.registerEvents(PlayersTeleportToOthers(), this)
        }

        if (config.getBoolean("auto-messages.enabled")) {
            val autoMessagesInterval = config.getLong("auto-messages.interval-seconds") * 20L
            AutoMessagesTimer().runTaskTimer(this, autoMessagesInterval, autoMessagesInterval)
        }
    }
}