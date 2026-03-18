package com.goyanov.essentials.main

import com.goyanov.essentials.automessages.AutoMessagesTimer
import com.goyanov.essentials.chat.EssentialsRGXChat
import com.goyanov.essentials.chat.commands.CommandLocalspy
import com.goyanov.essentials.global.commands.CommandEreload
import com.goyanov.essentials.global.managers.ConfigManager
import com.goyanov.essentials.rtp.CommandRtp
import com.goyanov.essentials.rtp.RandomTeleportViaCommand
import com.goyanov.essentials.tab.SendTabToPlayers
import com.goyanov.essentials.tab.TabUpdateTimer
import com.goyanov.essentials.tab.updateFullTab
import com.goyanov.essentials.tpa.CommandTpa
import com.goyanov.essentials.tpa.CommandTpaTabCompleter
import com.goyanov.essentials.tpa.CommandTpaccept
import com.goyanov.essentials.tpa.PlayersTeleportToOthers
import org.bukkit.Bukkit
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin

class EssentialsRGX : JavaPlugin() {

    companion object {
        private lateinit var instance : EssentialsRGX
        fun inst() = instance
    }

    fun reloadPlugin() {
        saveDefaultConfig()
        reloadConfig()

        ConfigManager.reloadAllConfigs()

        // auto-messages
        AutoMessagesTimer.stop()
        if (config.getBoolean("auto-messages.enabled")) {
            AutoMessagesTimer.start()
        }

        val papiEnabled = server.pluginManager.getPlugin("PlaceholderAPI") != null

        // tab
        PlayerJoinEvent.getHandlerList().unregister(SendTabToPlayers.getInstance())
        TabUpdateTimer.stop()
        if (config.getBoolean("tab.enabled")) {
            SendTabToPlayers.getInstance().papiEnabled = papiEnabled
            server.pluginManager.registerEvents(SendTabToPlayers.getInstance(), this)
            TabUpdateTimer.start(papiEnabled)
            Bukkit.getOnlinePlayers().forEach { player ->
                updateFullTab(player = player, papiEnabled = papiEnabled)
            }
        }

        // chat
        AsyncPlayerChatEvent.getHandlerList().unregister(EssentialsRGXChat.getInstance())
        if (config.getBoolean("chat.enabled")) {
            EssentialsRGXChat.getInstance().papiEnabled = papiEnabled
            server.pluginManager.registerEvents(EssentialsRGXChat.getInstance(), this)
        }
    }

    override fun onEnable() {
        instance = this

        reloadPlugin()

        val pm = server.pluginManager

        // commands.rtp
        getCommand("rtp")?.setExecutor(CommandRtp())
        pm.registerEvents(RandomTeleportViaCommand(), this)

        // commands.tpa
        getCommand("tpa")?.setExecutor(CommandTpa())
        getCommand("tpa")?.tabCompleter = CommandTpaTabCompleter()
        getCommand("tpaccept")?.setExecutor(CommandTpaccept())
        getCommand("tpaccept")?.tabCompleter = CommandTpaTabCompleter()
        getCommand("localspy")?.setExecutor(CommandLocalspy())
        pm.registerEvents(PlayersTeleportToOthers(), this)

        // commands.ereload
        getCommand("ereload")?.setExecutor(CommandEreload())
    }
}