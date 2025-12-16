package com.goyanov.essentials.managers

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
            val config = CustomConfig(type.fileName, true, EssentialsRGX.inst())
            configs[type] = config
            config
        }
    }
}

enum class ConfigType(val fileName: String) {
    TRANSLATIONS("translations.yml"),
}

fun translationsConfig(): FileConfiguration = ConfigManager.getConfig(ConfigType.TRANSLATIONS).config