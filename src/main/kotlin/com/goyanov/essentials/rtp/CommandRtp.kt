package com.goyanov.essentials.rtp

import com.goyanov.essentials.main.EssentialsRGX
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class CommandRtp : CommandExecutor {

    companion object {
        private val config = EssentialsRGX.Companion.inst().config
        private val cooldowns: HashMap<UUID, Long> = HashMap()
    }

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String?>): Boolean {

        if (sender !is Player) {
            sender.sendMessage("§cКоманда только для игроков!")
            return true
        }

        val timeRemain = cooldowns[sender.uniqueId]?.minus(System.currentTimeMillis())
        if (timeRemain != null && timeRemain > 0) {
            sender.sendMessage("§8§l| §cТы не можешь пользоваться случайной телепортацией ещё ${timeRemain/1000} секунд!")
            return true
        }

        val worldName = sender.world.name.lowercase()
        if (!sender.hasPermission("EssentialsRGX.command.rtp.world.$worldName")) {
            sender.sendMessage("§8§l| §cУ тебя нет прав на случайную телепортацию в этом мире!")
            return true
        }

        if (!sender.hasPermission("commands.rtp.unlimited")) {
            val configCmdSecondsDelay = config.getInt("commands.rtp.cooldown-seconds")
            cooldowns[sender.uniqueId] = System.currentTimeMillis() + configCmdSecondsDelay * 1000
            Bukkit.getScheduler().scheduleSyncDelayedTask(EssentialsRGX.Companion.inst(), {
                cooldowns.remove(sender.uniqueId)
            }, configCmdSecondsDelay * 20L)
        }

        val loc = RandomPlaceSearcher.findRandomLocation(sender.world)
        Bukkit.getPluginManager().callEvent(RandomTeleportEvent(sender, loc))
        return true
    }
}