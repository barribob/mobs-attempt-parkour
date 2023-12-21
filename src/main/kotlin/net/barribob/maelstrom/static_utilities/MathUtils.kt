package net.barribob.maelstrom.static_utilities

import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d

// https://stackoverflow.com/questions/23086291/format-in-kotlin-string-templates
fun Double.format(digits: Int) = "%.${digits}f".format(this)

object MathUtils {
    /**
     * Calls a function that linearly interpolates between two points. Includes both ends of the line
     *
     * Callback returns the position and the point number from 1 to points
     */
    fun lineCallback(start: Vec3d, end: Vec3d, points: Int, callback: (Vec3d, Int) -> Unit) {
        val dir: Vec3d = end.subtract(start).multiply(1 / (points - 1).toDouble())
        var pos = start
        for (i in 0 until points) {
            callback(pos, i)
            pos = pos.add(dir)
        }
    }
    fun directionToYaw(direction: Vec3d): Double {
        val x: Double = direction.x
        val z: Double = direction.z

        return Math.toDegrees(MathHelper.atan2(z, x))
    }
}