package net.barribob.parkour.ai

import net.minecraft.entity.ai.goal.Goal
import net.minecraft.entity.mob.MobEntity

class AIInjector {
    val injections = mutableMapOf<String, MutableList<(MobEntity) -> Pair<Int, Goal>>>()

    fun addGoalInjection(entityId: String, generator: (MobEntity) -> Pair<Int, Goal>) {
        if (!injections.containsKey(entityId)) {
            injections[entityId] = mutableListOf()
        }

        injections[entityId]?.add(generator)
    }
}