package com.goyanov.essentials.tpa

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class CommandTpaTabCompleter : TabCompleter {

    override fun onTabComplete(sender: CommandSender, cmd: Command, label: String, args: Array<out String?>?): List<String?>? {

        if (args!!.size == 1) {
            return null
        }

        return listOf()
    }
}