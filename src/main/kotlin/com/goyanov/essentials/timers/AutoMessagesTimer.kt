package com.goyanov.essentials.timers

import com.goyanov.essentials.managers.translationsConfig
import com.goyanov.rglib.RGLib
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

class AutoMessagesTimer() : BukkitRunnable() {

    private val messages = translationsConfig().getStringList("auto-messages.messages")
    private val prefix = translationsConfig().getString("auto-messages.prefix")
    private var currentIndex = 0

    override fun run() {

        if (messages.isEmpty()) {
            cancel()
            return
        }

        Bukkit.getOnlinePlayers().forEach { player ->
            player.sendMessage(RGLib.getColoredMessage("$prefix${messages[currentIndex]}"))
            if (++currentIndex == messages.size) {
                currentIndex = 0
            }
        }
    }
}