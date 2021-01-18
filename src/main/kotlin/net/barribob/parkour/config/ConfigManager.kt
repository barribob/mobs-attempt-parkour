package net.barribob.parkour.config

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonWriter
import net.barribob.maelstrom.MaelstromMod
import net.barribob.maelstrom.util.Version
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.reflect.Type
import java.nio.file.Files
import java.nio.file.StandardCopyOption

data class Config<T>(val data: T, val configVersion: String)

class ConfigManager {

    fun <T> handleConfigLoad(defaultConfig: Config<T>, typeToken: TypeToken<Config<T>>, modid: String): T {
        val directory = File("./config/${modid}/")
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val configFile = File(directory, "config.json")
        val gson = Gson()
        val configType: Type? = typeToken.type

        if (configFile.exists()) {
            try {
                val existingConfig: Config<T>? = FileReader(configFile).use { reader -> gson.fromJson(reader, configType) }

                if (existingConfig != null) {
                    try {
                        if (Version(defaultConfig.configVersion) <= Version(existingConfig.configVersion)) {
                            return existingConfig.data
                        } else {
                            val configBackup = File(directory, "config_${existingConfig.configVersion}.json")
                            Files.copy(configFile.toPath(), configBackup.toPath(), StandardCopyOption.REPLACE_EXISTING)
                            MaelstromMod.LOGGER.warn("Config file for $modid is outdated. Created backup of config (${configBackup.toRelativeString(directory)}), and using new default.")
                        }
                    } catch (e: IllegalArgumentException) {
                        MaelstromMod.LOGGER.error("Failed to read config file for $modid! Perhaps the config version has been tampered with?")
                        return defaultConfig.data
                    }
                }
            }
            catch (e: Exception) {
                MaelstromMod.LOGGER.error("Failed to read config file for $modid!")
                MaelstromMod.LOGGER.error(e)
                return defaultConfig.data
            }
        }

        JsonWriter(FileWriter(configFile)).use { writer ->
            writer.setIndent("\t")
            gson.toJson(defaultConfig, configType, writer)
        }

        return defaultConfig.data
    }
}