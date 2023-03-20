package net.barribob.parkour

import net.barribob.parkour.ai.BlockType
import net.barribob.maelstrom.static_utilities.yOffset
import net.minecraft.block.*
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.pathing.NavigationType
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.registry.tag.BlockTags
import net.minecraft.registry.tag.FluidTags
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World

object ModUtils {
    fun getJumpVelocity(world: World, entity: LivingEntity): Double {
        var baseVelocity = 0.42 * getJumpVelocityMultiplier(world, entity)
        if (entity.hasStatusEffect(StatusEffects.JUMP_BOOST)) {
            baseVelocity += 0.1 * (entity.getStatusEffect(StatusEffects.JUMP_BOOST)!!.amplifier + 1)
        }
        return baseVelocity
    }

    private fun getJumpVelocityMultiplier(world: World, entity: LivingEntity): Double {
        val f: Float = world.getBlockState(entity.blockPos).block.jumpVelocityMultiplier
        val g: Float = world.getBlockState(getVelocityAffectingPos(entity)).block.jumpVelocityMultiplier
        return if (f.toDouble() == 1.0) g.toDouble() else f.toDouble()
    }

    private fun getVelocityAffectingPos(entity: LivingEntity): BlockPos {
        return floorBlockPos(entity.pos.x, entity.boundingBox.minY - 0.5000001, entity.pos.z)
    }
    
    private fun floorBlockPos(x: Double, y: Double, z: Double) : BlockPos {
        return BlockPos(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z))
    }

    fun floorBlockPos(pos: Vec3d) : BlockPos {
        return floorBlockPos(pos.x, pos.y, pos.z)
    }
    
    fun leapTowards(entity: LivingEntity, target: Vec3d, horzVel: Double, yVel: Double) {
        val dir = target.subtract(entity.pos).normalize()
        val leap: Vec3d = Vec3d(dir.x, 0.0, dir.z).normalize().multiply(horzVel).yOffset(yVel)
        val clampedYVelocity = if (entity.velocity.y < 0.1) leap.y else 0.0

        // Normalize to make sure the velocity doesn't go beyond what we expect
        var horzVelocity = entity.velocity.add(leap.x, 0.0, leap.z)
        val scale = horzVel / horzVelocity.length()
        if (scale < 1) {
            horzVelocity = horzVelocity.multiply(scale)
        }

        entity.velocity = horzVelocity.yOffset(clampedYVelocity)
    }

    fun getBlockType(world: BlockView, pos: BlockPos, callsLeft: Int): BlockType {
        val blockState = world.getBlockState(pos)
        val block = blockState.block
        val material = blockState.material
        val fluidState = world.getFluidState(pos)
        val belowType = if(pos.y > 0 && callsLeft > 0) getBlockType(world, pos.down(), callsLeft - 1) else BlockType.OPEN

        return when {
            blockState.isOf(Blocks.SWEET_BERRY_BUSH) ||
                    blockState.isIn(BlockTags.FIRE) ||
                    CampfireBlock.isLitCampfire(blockState) ||
                    fluidState.isIn(FluidTags.WATER) -> BlockType.PASSABLE_OBSTACLE
            fluidState.isIn(FluidTags.LAVA) ||
                    blockState.isOf(Blocks.CACTUS) ||
                    blockState.isOf(Blocks.HONEY_BLOCK) ||
                    blockState.isOf(Blocks.MAGMA_BLOCK) -> BlockType.SOLID_OBSTACLE
            block is LeavesBlock ||
                    blockState.isIn(BlockTags.FENCES) ||
                    blockState.isIn(BlockTags.WALLS) ||
                    (block is FenceGateBlock && !blockState.get(FenceGateBlock.OPEN)) ||
                    (DoorBlock.isWoodenDoor(blockState) && !blockState.get(DoorBlock.OPEN)) ||
                    (block is DoorBlock && material == Material.METAL && !blockState.get(DoorBlock.OPEN)) ||
                    (block is DoorBlock && blockState.get(DoorBlock.OPEN)) ||
                    !blockState.canPathfindThrough(world, pos, NavigationType.LAND) -> BlockType.BLOCKED
            belowType == BlockType.BLOCKED -> BlockType.WALKABLE
            belowType == BlockType.OPEN -> BlockType.PASSABLE_OBSTACLE
            belowType == BlockType.PASSABLE_OBSTACLE -> BlockType.PASSABLE_OBSTACLE
            belowType == BlockType.SOLID_OBSTACLE -> BlockType.PASSABLE_OBSTACLE
            else -> BlockType.OPEN
        }
    }

    fun findClosestCorner(point: Vec3d, shape: VoxelShape, maxSamples: Int): Vec3d? {
        val corners = shape.boundingBoxes.flatMap { getTopCornersAndEdges(it) }.shuffled().take(maxSamples)
        return corners.minByOrNull { it.squaredDistanceTo(point) }
    }

    private fun getTopCornersAndEdges(box: Box): List<Vec3d> {
        val halfX = box.xLength * 0.5
        val halfZ = box.zLength * 0.5

        return listOf(
            Vec3d(box.minX, box.maxY, box.minZ),
            Vec3d(box.maxX, box.maxY, box.minZ),
            Vec3d(box.minX, box.maxY, box.maxZ),
            Vec3d(box.maxX, box.maxY, box.maxZ),
            Vec3d(box.minX + halfX, box.maxY, box.minZ),
            Vec3d(box.minX, box.maxY, box.minZ + halfZ),
            Vec3d(box.maxX, box.maxY, box.minZ + halfZ),
            Vec3d(box.minX + halfX, box.maxY, box.maxZ)
        )
    }
}