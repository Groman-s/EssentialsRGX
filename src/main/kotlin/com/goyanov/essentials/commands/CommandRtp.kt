package com.goyanov.essentials.commands

import com.goyanov.essentials.main.EssentialsRGX
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CommandRtp : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String?>): Boolean {

        if (sender !is Player) {
            sender.sendMessage("Команда только для игроков!")
            return true
        }

        val config = EssentialsRGX.inst().config

        val minRadius = config.getInt("commands.rtp.radius.min")
        val maxRadius = config.getInt("commands.rtp.radius.max")

        val x = minRadius + Math.random() * (maxRadius - minRadius) + 0.5
        val z = minRadius + Math.random() * (maxRadius - minRadius) + 0.5
        val y = sender.world.getHighestBlockYAt(x.toInt(), z.toInt()) + 1

        val loc = Location(sender.world, x, y.toDouble(), z)
        sender.teleport(loc)

        return true
    }
}