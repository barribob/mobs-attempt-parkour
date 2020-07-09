package net.barribob.parkour

import com.google.gson.reflect.TypeToken
import net.barribob.maelstrom.MaelstromMod
import net.barribob.maelstrom.adapters.GoalAdapter
import net.barribob.maelstrom.config.Config
import net.barribob.maelstrom.mob.server.ai.JumpToTargetGoal
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback
import net.minecraft.entity.EntityType
import net.minecraft.util.registry.Registry

/**
 * Issues: Massive lag spike first time parkour activates
 */

data class AiInfo(val priority: Int = 0, val mobId: String)

object Parkour {
    const val MODID = "mobs_attempt_parkour"
    const val VERSION = "0.2"
}

@Suppress("unused")
fun init() {
    val defaultMobs = listOf(
            Pair(1, EntityType.ZOMBIE),
            Pair(1, EntityType.ZOMBIE_VILLAGER),
            Pair(1, EntityType.ZOMBIFIED_PIGLIN),
            Pair(1, EntityType.SILVERFISH),
            Pair(1, EntityType.ENDERMITE),
            Pair(1, EntityType.ENDERMAN),
            Pair(1, EntityType.WITHER_SKELETON),
            Pair(1, EntityType.SKELETON),
            Pair(2, EntityType.SPIDER),
            Pair(2, EntityType.CAVE_SPIDER),
            Pair(1, EntityType.PILLAGER),
            Pair(1, EntityType.VINDICATOR),
            Pair(3, EntityType.ILLUSIONER),
            Pair(2, EntityType.WITCH),
            Pair(0, EntityType.IRON_GOLEM),
            Pair(3, EntityType.CREEPER)
    ).map { AiInfo(it.first, EntityType.getId(it.second).toString()) }

    val configType = object : TypeToken<Config<List<AiInfo>>>() {}
    val config = Config(defaultMobs, Parkour.VERSION)
    val configData = MaelstromMod.configManager.handleConfigLoad(config, configType, Parkour.MODID)

    configData.forEach {
        MaelstromMod.aiManager.addGoalInjection(it.mobId) { entity ->
            Pair(it.priority, GoalAdapter(JumpToTargetGoal(entity)))
        }
    }
}