package com.EmosewaPixel.pixellib.miscutils

import net.minecraft.entity.item.ItemEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

//This class contains useful functions for working with items
object ItemUtils {
    @JvmStatic
    fun spawnItemInWorld(world: World, pos: BlockPos, stack: ItemStack) {
        world.addEntity(ItemEntity(world, (pos.x + 0.5f).toDouble(),
                (pos.y + 0.5f).toDouble(), (pos.z + 0.5f).toDouble(), stack))
    }
}