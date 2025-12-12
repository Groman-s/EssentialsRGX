package com.goyanov.essentials.tpa

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CommandTpa : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String?>): Boolean {

        if (sender !is Player) {
            sender.sendMessage("§cКоманда только для игроков!")
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage("§8§l| §c/tpa <игрок>")
            return true
        }

        val target = Bukkit.getPlayer(args[0]!!) ?: run {
            sender.sendMessage("§8§l| §cТакого игрока нет на сервере!")
            return true
        }

        if (target == sender) {
            sender.sendMessage("§8§l| §cНельзя отправить запрос на телепортацию самому себе!")
            return true
        }

        if (TpaActiveRequests.exists(playerInitiator = sender, playerTarget = target)) {
            sender.sendMessage("§8§l| §cЗапрос на телепортацию уже был отправлен этому игроку!")
            return true
        }

        Bukkit.getPluginManager().callEvent(PlayerTpaSendEvent(tpaInitiator = sender, tpaTarget = target))
        return true
    }
}