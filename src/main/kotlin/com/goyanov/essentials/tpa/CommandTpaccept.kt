package com.goyanov.essentials.tpa

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CommandTpaccept : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String?>): Boolean {

        if (sender !is Player) {
            sender.sendMessage("§cКоманда только для игроков!")
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage("§8§l| §c/tpaccept <игрок>")
            return true
        }

        val target = Bukkit.getPlayer(args[0]!!) ?: run {
            sender.sendMessage("§8§l| §cТакого игрока нет на сервере!")
            return true
        }

        if (!TpaActiveRequests.exists(playerInitiator = target, playerTarget = sender)) {
            sender.sendMessage("§8§l| §cЭтот игрок не отправлял тебе запрос на телепортацию!")
            return true
        }

        Bukkit.getPluginManager().callEvent(PlayerTpaAcceptEvent(tpaInitiator = target, tpaTarget = sender))
        return true
    }
}