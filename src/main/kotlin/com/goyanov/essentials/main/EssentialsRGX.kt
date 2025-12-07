package com.goyanov.essentials.main

import com.goyanov.essentials.rtp.CommandRtp
import com.goyanov.essentials.rtp.RandomTeleportViaCommand
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
    }
}