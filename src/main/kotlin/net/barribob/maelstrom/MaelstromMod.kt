package net.barribob.maelstrom

import net.barribob.maelstrom.adapters.GoalAdapter
import net.barribob.maelstrom.general.EventScheduler
import net.barribob.maelstrom.mob.AIManager
import net.barribob.maelstrom.mob.server.ai.JumpToTargetGoal
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.event.server.ServerTickCallback
import net.minecraft.entity.EntityType
import net.minecraft.entity.ai.goal.FollowTargetGoal
import net.minecraft.entity.passive.VillagerEntity
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object MaelstromMod {
    const val MODID = "maelstrom"

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

    MaelstromMod.aiManager.addGoalInjection(EntityType.SILVERFISH) { entity -> Pair(1, GoalAdapter(JumpToTargetGoal(entity))) }
    MaelstromMod.aiManager.addTargetInjection(EntityType.SILVERFISH) { entity -> Pair(3, FollowTargetGoal(entity, VillagerEntity::class.java, true)) }

    MaelstromMod.aiManager.addGoalInjection(EntityType.SPIDER) { entity -> Pair(2, GoalAdapter(JumpToTargetGoal(entity))) }
    MaelstromMod.aiManager.addTargetInjection(EntityType.SPIDER) { entity -> Pair(4, FollowTargetGoal(entity, VillagerEntity::class.java, true)) }

    MaelstromMod.aiManager.addGoalInjection(EntityType.VINDICATOR) { entity -> Pair(1, GoalAdapter(JumpToTargetGoal(entity))) }
    MaelstromMod.aiManager.addGoalInjection(EntityType.ILLUSIONER) { entity -> Pair(3, GoalAdapter(JumpToTargetGoal(entity))) }
}