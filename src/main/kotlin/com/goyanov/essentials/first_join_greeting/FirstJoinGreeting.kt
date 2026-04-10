package com.goyanov.essentials.first_join_greeting

import com.goyanov.essentials.global.managers.ConfigManager
import com.goyanov.essentials.global.managers.ConfigType
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class FirstJoinGreeting private constructor() : Listener {

    companion object {
        private var instance: FirstJoinGreeting? = null

        fun getInstance(): FirstJoinGreeting {
            instance ?: run { instance = FirstJoinGreeting() }
            return instance!!
        }
    }

    @EventHandler
    fun sendOnJoin(e: PlayerJoinEvent) {
        if (!e.player.hasPlayedBefore()) {
            Bukkit.getOnlinePlayers().forEach { p ->
                p.sendMessage(ConfigManager
                    .getConfig(ConfigType.TRANSLATIONS)
                    .getColoredConfigString("first-join-greeting").replace("{player}", e.player.name)
                )
            }
        }
    }
}