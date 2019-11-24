package com.emosewapixel.pixellib.blocks

import com.emosewapixel.pixellib.extensions.toResLoc
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.ItemStack
import net.minecraft.world.storage.loot.LootContext

//Mod Block is used for creating a regular Block with a specified registry name and harvest level
open class ModBlock(properties: Properties, name: String) : Block(properties) {
    init {
        registryName = name.toResLoc()
    }

    override fun getDrops(state: BlockState, builder: LootContext.Builder) = listOf(ItemStack(this))
}