package net.barribob.parkour

import net.barribob.parkour.adapters.GoalAdapter
import net.barribob.parkour.general.EventScheduler
import net.barribob.parkour.mob.AIManager
import net.barribob.parkour.mob.server.ai.JumpToTargetGoal
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.event.server.ServerTickCallback
import net.minecraft.entity.EntityType
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object MaelstromMod {
    const val MODID = "mobs_attempt_parkour"

    @Environment(EnvType.SERVER)
    val serverEventScheduler = EventScheduler()

    @Environment(EnvType.SERVER)
    val aiManager = AIManager()

    val LOGGER: Logger = LogManager.getLogger()
}

@Suppress("unused")
fun init() {
    ServerTickCallback.EVENT.register(ServerTickCallback { MaelstromMod.serverEventScheduler.updateEvents() })

    MaelstromMod.aiManager.addGoalInjection(EntityType.ZOMBIE) { entity -> Pair(1, GoalAdapter(JumpToTargetGoal(entity))) }
    MaelstromMod.aiManager.addGoalInjection(EntityType.ZOMBIE_VILLAGER) { entity -> Pair(1, GoalAdapter(JumpToTargetGoal(entity))) }
    MaelstromMod.aiManager.addGoalInjection(EntityType.ZOMBIFIED_PIGLIN) { entity -> Pair(1, GoalAdapter(JumpToTargetGoal(entity))) }

    MaelstromMod.aiManager.addGoalInjection(EntityType.SILVERFISH) { entity -> Pair(1, GoalAdapter(JumpToTargetGoal(entity))) }

    MaelstromMod.aiManager.addGoalInjection(EntityType.ENDERMITE) { entity -> Pair(1, GoalAdapter(JumpToTargetGoal(entity))) }

    MaelstromMod.aiManager.addGoalInjection(EntityType.ENDERMAN) { entity -> Pair(1, GoalAdapter(JumpToTargetGoal(entity))) }

    MaelstromMod.aiManager.addGoalInjection(EntityType.WITHER_SKELETON) { entity -> Pair(1, GoalAdapter(JumpToTargetGoal(entity))) }
    MaelstromMod.aiManager.addGoalInjection(EntityType.SKELETON) { entity -> Pair(1, GoalAdapter(JumpToTargetGoal(entity))) }

    MaelstromMod.aiManager.addGoalInjection(EntityType.SPIDER) { entity -> Pair(2, GoalAdapter(JumpToTargetGoal(entity))) }
    MaelstromMod.aiManager.addGoalInjection(EntityType.CAVE_SPIDER) { entity -> Pair(2, GoalAdapter(JumpToTargetGoal(entity))) }

    MaelstromMod.aiManager.addGoalInjection(EntityType.PILLAGER) { entity -> Pair(1, GoalAdapter(JumpToTargetGoal(entity))) }
    MaelstromMod.aiManager.addGoalInjection(EntityType.VINDICATOR) { entity -> Pair(1, GoalAdapter(JumpToTargetGoal(entity))) }
    MaelstromMod.aiManager.addGoalInjection(EntityType.ILLUSIONER) { entity -> Pair(3, GoalAdapter(JumpToTargetGoal(entity))) }
    MaelstromMod.aiManager.addGoalInjection(EntityType.WITCH) { entity -> Pair(2, GoalAdapter(JumpToTargetGoal(entity))) }

    MaelstromMod.aiManager.addGoalInjection(EntityType.IRON_GOLEM) { entity -> Pair(0, GoalAdapter(JumpToTargetGoal(entity))) }
    MaelstromMod.aiManager.addGoalInjection(EntityType.CREEPER) { entity -> Pair(3, GoalAdapter(JumpToTargetGoal(entity))) }
}