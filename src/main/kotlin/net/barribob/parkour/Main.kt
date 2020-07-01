package net.barribob.parkour

import net.barribob.maelstrom.MaelstromMod
import net.barribob.maelstrom.adapters.GoalAdapter
import net.barribob.maelstrom.mob.server.ai.JumpToTargetGoal
import net.minecraft.entity.EntityType

@Suppress("unused")
fun init() {
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