package com.scientianovateam.versatile.resources

import com.google.common.collect.ListMultimap
import com.google.common.collect.MultimapBuilder
import net.minecraft.block.Block
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation

//This class contains functions used for adding Item and Block Tags to the fake data pack
object TagMaps {
    @JvmField
    val ITEM_TAGS: ListMultimap<ResourceLocation, Item> = MultimapBuilder.treeKeys().arrayListValues().build<ResourceLocation, Item>()

    @JvmField
    val BLOCK_TAGS: ListMultimap<ResourceLocation, Block> = MultimapBuilder.treeKeys().arrayListValues().build<ResourceLocation, Block>()

    @JvmField
    val FLUID_TAGS: ListMultimap<ResourceLocation, Fluid> = MultimapBuilder.treeKeys().arrayListValues().build<ResourceLocation, Fluid>()

    @JvmStatic
    fun addItemToTag(tag: ResourceLocation, item: Item) = ITEM_TAGS.put(tag, item)

    @JvmStatic
    fun addBlockToTag(tag: ResourceLocation, block: Block) = BLOCK_TAGS.put(tag, block)

    @JvmStatic
    fun addFluidToTag(tag: ResourceLocation, fluid: Fluid) = FLUID_TAGS.put(tag, fluid)
}