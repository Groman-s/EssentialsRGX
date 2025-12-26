package com.goyanov.essentials.global.commands

import com.goyanov.essentials.main.EssentialsRGX
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class CommandEreload : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String?>?): Boolean {
        EssentialsRGX.inst().reloadPlugin()
        sender.sendMessage("§8§l| §aПлагин успешно перезагружен!")
        return true
    }
}