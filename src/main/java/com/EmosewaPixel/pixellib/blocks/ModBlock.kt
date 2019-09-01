package com.EmosewaPixel.pixellib.blocks

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.ItemStack
import net.minecraft.world.storage.loot.LootContext
import net.minecraftforge.common.ToolType

import java.util.Collections

//Mod Block is used for creating a regular Block with a specified registry name and harvest level
open class ModBlock(properties: Block.Properties, name: String, level: Int) : Block(properties.harvestTool(ToolType.PICKAXE).harvestLevel(level)) {
    init {
        setRegistryName(name)
    }

    override fun getDrops(state: BlockState, builder: LootContext.Builder): List<ItemStack> {
        return listOf(ItemStack(this))
    }
}