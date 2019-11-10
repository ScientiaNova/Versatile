package com.emosewapixel.pixellib.extensions

import net.minecraft.entity.item.ItemEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

//This class contains useful functions for working with items
fun World.spawnItemInWorld(pos: BlockPos, stack: ItemStack) = addEntity(ItemEntity(this, (pos.x + 0.5f).toDouble(), (pos.y + 0.5f).toDouble(), (pos.z + 0.5f).toDouble(), stack))