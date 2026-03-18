package com.goyanov.essentials.global.managers

import com.goyanov.essentials.main.EssentialsRGX
import com.goyanov.rglib.CustomConfig
import org.bukkit.configuration.file.FileConfiguration

class ConfigManager {

    companion object {
        private val configs = HashMap<ConfigType, CustomConfig>()

        fun reloadAllConfigs() {
            configs.clear()
        }

        fun getConfig(type: ConfigType) : CustomConfig = configs[type] ?: run {
            val config = CustomConfig(type.fileName, type.fromJar, EssentialsRGX.inst())
            configs[type] = config
            config
        }
    }
}

enum class ConfigType(val fileName: String, val fromJar: Boolean) {
    TRANSLATIONS("translations.yml", true),
    PLAYERS("players.yml", false),
}

fun translationsConfig(): FileConfiguration = ConfigManager.getConfig(ConfigType.TRANSLATIONS).config

fun playersConfig(): FileConfiguration = ConfigManager.getConfig(ConfigType.PLAYERS).config
