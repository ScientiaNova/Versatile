package com.scientianova.versatile.blocks

import com.scientianovateam.versatile.common.extensions.toResLoc
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.client.renderer.RenderType
import net.minecraft.item.ItemStack
import net.minecraft.world.storage.loot.LootContext
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

//Mod Block is used for creating a regular Block with a specified registry name and harvest level
open class ModBlock(properties: Properties, name: String) : Block(properties) {
    init {
        registryName = name.toResLoc()
    }

    @OnlyIn(Dist.CLIENT)
    open fun getRenderLayer(): RenderType = RenderType.getSolid()

    override fun getDrops(state: BlockState, builder: LootContext.Builder) = listOf(ItemStack(this))
}