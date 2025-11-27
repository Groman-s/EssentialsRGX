package com.goyanov.essentials.commands

import com.goyanov.essentials.main.EssentialsRGX
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CommandRtp : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String?>): Boolean {

        if (sender !is Player) {
            sender.sendMessage("§cКоманда только для игроков!")
            return true
        }

        val worldName = sender.world.name.lowercase()
        if (!sender.hasPermission("EssentialsRGX.command.rtp.world.$worldName")) {
            sender.sendMessage("§cУ тебя нет прав на случайную телепортацию в этом мире!")
            return true
        }

        val config = EssentialsRGX.inst().config

        val minRadius = config.getInt("commands.rtp.radius.min")
        val maxRadius = config.getInt("commands.rtp.radius.max")

        val blockRegexBlackList = config.getStringList("commands.rtp.unsafe-blocks-regex")

        var highestLoc: Location
        var teleportLoc: Location

        do {
            val x = (minRadius + Math.random() * (maxRadius - minRadius)).toInt() + 0.5
            val z = (minRadius + Math.random() * (maxRadius - minRadius)).toInt() + 0.5
            val y = sender.world.getHighestBlockYAt(x.toInt(), z.toInt())

            highestLoc = Location(sender.world, x, y.toDouble(), z)
            teleportLoc = highestLoc.clone().add(0.0, 1.0, 0.0)
        } while (
            blockRegexBlackList.any { highestLoc.block.type.name.matches(Regex(it)) }
                ||
            blockRegexBlackList.any { teleportLoc.block.type.name.matches(Regex(it)) }
        )

        sender.teleport(teleportLoc)

        return true
    }
}