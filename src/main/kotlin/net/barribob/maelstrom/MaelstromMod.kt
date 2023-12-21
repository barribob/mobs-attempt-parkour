package net.barribob.maelstrom

import net.barribob.maelstrom.general.event.EventScheduler
import net.barribob.maelstrom.general.io.ConsoleLogger
import net.barribob.maelstrom.general.io.ILogger
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.util.Identifier
import org.apache.logging.log4j.LogManager

object MaelstromMod {
    val LOGGER: ILogger = ConsoleLogger(LogManager.getLogger())
}