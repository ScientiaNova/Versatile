package com.EmosewaPixel.pixellib.blocks

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.ItemStack
import net.minecraft.world.storage.loot.LootContext
import net.minecraftforge.common.ToolType

//Mod Block is used for creating a regular Block with a specified registry name and harvest level
open class ModBlock(properties: Properties, name: String, level: Int) : Block(properties.harvestTool(ToolType.PICKAXE).harvestLevel(level)) {
    init {
        setRegistryName(name)
    }

    override fun getDrops(state: BlockState, builder: LootContext.Builder) = listOf(ItemStack(this))
}