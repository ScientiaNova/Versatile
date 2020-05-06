@file:JvmName("TagMaps")

package com.scientianova.versatile.resources

import com.google.common.collect.ListMultimap
import com.google.common.collect.MultimapBuilder
import com.scientianova.versatile.common.extensions.toResLoc
import net.minecraft.block.Block
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation

val ITEM_TAGS: ListMultimap<ResourceLocation, Item> = MultimapBuilder.treeKeys().arrayListValues().build()
val BLOCK_TAGS: ListMultimap<ResourceLocation, Block> = MultimapBuilder.treeKeys().arrayListValues().build()
val FLUID_TAGS: ListMultimap<ResourceLocation, Fluid> = MultimapBuilder.treeKeys().arrayListValues().build()

fun addItemToTag(tag: ResourceLocation, item: Item) = ITEM_TAGS.put(tag, item)
fun addItemToTag(tag: String, item: Item) = addItemToTag(tag.toResLoc(), item)

fun addBlockToTag(tag: ResourceLocation, block: Block) = BLOCK_TAGS.put(tag, block)
fun addBlockToTag(tag: String, block: Block) = addBlockToTag(tag.toResLoc(), block)

fun addFluidToTag(tag: ResourceLocation, fluid: Fluid) = FLUID_TAGS.put(tag, fluid)
fun addFluidToTag(tag: String, fluid: Fluid) = addFluidToTag(tag.toResLoc(), fluid)