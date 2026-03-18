package com.goyanov.essentials.chat.commands

import com.goyanov.essentials.global.managers.ConfigManager
import com.goyanov.essentials.global.managers.ConfigType
import com.goyanov.essentials.global.managers.playersConfig
import com.goyanov.essentials.global.managers.translationsConfig
import com.goyanov.rglib.RGLib
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CommandLocalspy : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String?>?): Boolean {

        if (sender !is Player) {
            sender.sendMessage("§cКоманда только для игроков!")
            return true
        }

        val newStatus = !playersConfig().getBoolean("${sender.name.lowercase()}.localspy")

        if (newStatus) {
            sender.sendMessage(RGLib.getColoredMessage(translationsConfig().getString("chat.localspy.enabled")))
        } else {
            sender.sendMessage(RGLib.getColoredMessage(translationsConfig().getString("chat.localspy.disabled")))
        }

        playersConfig().set("${sender.name.lowercase()}.localspy", newStatus)
        ConfigManager.getConfig(ConfigType.PLAYERS).save()
        return true
    }
}