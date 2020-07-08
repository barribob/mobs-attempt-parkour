package net.barribob.parkour

import com.google.gson.reflect.TypeToken
import net.barribob.maelstrom.MaelstromMod
import net.barribob.maelstrom.adapters.GoalAdapter
import net.barribob.maelstrom.config.Config
import net.barribob.maelstrom.mob.server.ai.JumpToTargetGoal
import net.minecraft.entity.EntityType
import net.minecraft.entity.mob.MobEntity
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * Issues: Massive lag spike first time parkour activates
 */

data class AiInfo(val priority: Int = 0, val mobId: String)

object Parkour {
    const val MODID = "mobs_attempt_parkour"
    const val VERSION = "0.2"
    val LOGGER: Logger = LogManager.getLogger()
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
        val entityType = EntityType.get(it.mobId)
        if (entityType.isPresent) {
            try {
                val mobType = entityType.get() as EntityType<out MobEntity>
                MaelstromMod.aiManager.addGoalInjection(mobType) { entity -> Pair(it.priority, GoalAdapter(JumpToTargetGoal(entity))) }
            } catch (e: Exception) {
                Parkour.LOGGER.warn("Failed to add jumping ai to ${it.mobId}")
                e.printStackTrace()
            }
        }
        else {
            Parkour.LOGGER.warn("Could not find ${it.mobId} to apply jumping ai to")
        }
    }
}