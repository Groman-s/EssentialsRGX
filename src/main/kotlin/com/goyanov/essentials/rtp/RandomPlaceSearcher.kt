package com.goyanov.essentials.rtp

import com.goyanov.essentials.main.EssentialsRGX
import org.bukkit.Location
import org.bukkit.World

class RandomPlaceSearcher {

    companion object {
        private val config = EssentialsRGX.Companion.inst().config

        fun findRandomLocation(world: World) : Location {
            val minRadius = config.getInt("commands.rtp.radius.min")
            val maxRadius = config.getInt("commands.rtp.radius.max")

            val x = (if (Math.random() < 0.5) 1 else -1) * (minRadius + Math.random() * (maxRadius - minRadius)).toInt() + 0.5
            val z = (if (Math.random() < 0.5) 1 else -1) * (minRadius + Math.random() * (maxRadius - minRadius)).toInt() + 0.5

            val y = world.getHighestBlockYAt(x.toInt(), z.toInt()).toDouble() + 1

            return Location(world, x, y, z)
        }
    }
}