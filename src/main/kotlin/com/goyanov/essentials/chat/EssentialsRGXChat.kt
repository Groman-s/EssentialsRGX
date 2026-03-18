package com.goyanov.essentials.chat

import com.goyanov.essentials.global.managers.translationsConfig
import com.goyanov.essentials.main.EssentialsRGX
import com.goyanov.rglib.RGLib
import java.util.UUID
import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class EssentialsRGXChat private constructor(var papiEnabled: Boolean) : Listener {

    companion object {
        private var instance: EssentialsRGXChat? = null
        private var cooldowns = mutableMapOf<UUID, Long>()

        fun getInstance(): EssentialsRGXChat {
            instance ?: run { instance = EssentialsRGXChat(false) }
            return instance!!
        }
    }

    @EventHandler
    fun onChat(e: AsyncPlayerChatEvent) {

        val canSpam = e.player.hasPermission("EssentialsRGX.chat.no-cooldown")

        if (!canSpam) {
            cooldowns[e.player.uniqueId]?.let { time ->
                val remain = (time - System.currentTimeMillis()) / 1000
                e.player.sendMessage(RGLib.getColoredMessage(translationsConfig().getString("chat.cooldown")!!.replace("{seconds}", "$remain")))
                e.isCancelled = true
                return
            }
        }

        var message = e.message.trim()

        val messageIsLocal = !message.startsWith("!")

        if (!messageIsLocal) {
            message = message.substring(1).trim()
        }

        if (message.isBlank()) {
            e.isCancelled = true
            return
        }

        if (messageIsLocal) {
            val radius = EssentialsRGX.inst().config.getInt("chat.local-chat-radius")
            e.recipients.removeIf { p -> p.world != e.player.world || p.location.distance(e.player.location) > radius }
        }

        val formatPrefix =
            if (messageIsLocal) EssentialsRGX.inst().config.getString("chat.prefixes.local-chat")
            else EssentialsRGX.inst().config.getString("chat.prefixes.global-chat")

        var format = formatPrefix + EssentialsRGX.inst().config.getString("chat.format")!!

        if (papiEnabled) {
            format = PlaceholderAPI.setPlaceholders(e.player, format)
        }

        e.message = RGLib.getColoredMessage(message)
        e.format = RGLib.getColoredMessage(format)

        if (e.recipients.size == 1) {
            e.player.sendMessage(RGLib.formatWithSimpleColors(translationsConfig().getString("chat.no-recipients-notification.chat")))
            RGLib.sendActionBarMessage(e.player, RGLib.formatWithSimpleColors(translationsConfig().getString("chat.no-recipients-notification.action-bar")))
            e.player.playSound(e.player.location, Sound.valueOf(EssentialsRGX.inst().config.getString("chat.no-recipients-sound")!!), 1f, 1f)
        }

        if (!canSpam) {
            val cooldownSeconds = EssentialsRGX.inst().config.getInt("chat.cooldown-seconds")
            cooldowns[e.player.uniqueId] = System.currentTimeMillis() + cooldownSeconds * 1000
            Bukkit.getScheduler().scheduleSyncDelayedTask(EssentialsRGX.inst(), {
                cooldowns.remove(e.player.uniqueId)
            }, cooldownSeconds * 20L)
        }
    }
}