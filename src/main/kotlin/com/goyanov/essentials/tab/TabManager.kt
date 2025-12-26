package com.goyanov.essentials.tab

import com.goyanov.essentials.global.managers.translationsConfig
import com.goyanov.rglib.RGLib
import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.entity.Player

fun sendCustomHeaders(player: Player, papiEnabled: Boolean) {
    var header = translationsConfig().getStringList("tab.headers").reduce { s1, s2 -> "${s1}\n${s2}" }
    if (papiEnabled) { header = PlaceholderAPI.setPlaceholders(player, header) }
    player.playerListHeader = RGLib.getColoredMessage(header)
}

fun sendCustomFooters(player: Player, papiEnabled: Boolean) {
    var footer = translationsConfig().getStringList("tab.footers").reduce { s1, s2 -> "${s1}\n${s2}" }
    if (papiEnabled) { footer = PlaceholderAPI.setPlaceholders(player, footer) }
    player.playerListFooter = RGLib.getColoredMessage(footer)
}

fun updatePlayerTabName(player: Player, papiEnabled: Boolean) {
    var tabName = translationsConfig().getString("tab.player-name")!!
    if (papiEnabled) { tabName = PlaceholderAPI.setPlaceholders(player, tabName) }
    player.setPlayerListName(RGLib.getColoredMessage(tabName))
}

fun updateFullTab(player: Player, papiEnabled: Boolean) {
    sendCustomHeaders(player = player, papiEnabled = papiEnabled)
    sendCustomFooters(player = player, papiEnabled = papiEnabled)
    updatePlayerTabName(player = player, papiEnabled = papiEnabled)
}