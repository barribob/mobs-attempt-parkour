package net.barribob.maelstrom.static_utilities

import net.minecraft.util.math.Vec3d
import kotlin.random.Random

object RandomUtils {
    private val rand = Random

    /**
     * Creates a random value between -range and range
     */
    fun double(range: Double): Double {
        return (rand.nextDouble() - 0.5) * 2 * range
    }
}
