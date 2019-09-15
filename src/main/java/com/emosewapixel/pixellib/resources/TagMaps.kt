package com.emosewapixel.pixellib.resources

import com.emosewapixel.pixellib.materialsystem.materials.FluidMaterial
import com.emosewapixel.pixellib.materialsystem.materials.IMaterialObject
import com.google.common.collect.ListMultimap
import com.google.common.collect.MultimapBuilder
import net.minecraft.block.Block
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item

//This class contains functions used for adding Item and Block Tags to the fake data pack
object TagMaps {
    @JvmField
    val ITEM_TAGS: ListMultimap<String, Item> = MultimapBuilder.treeKeys().arrayListValues().build<String, Item>()

    @JvmField
    val BLOCK_TAGS: ListMultimap<String, Block> = MultimapBuilder.treeKeys().arrayListValues().build<String, Block>()

    @JvmField
    val FLUID_TAGS: ListMultimap<String, Fluid> = MultimapBuilder.treeKeys().arrayListValues().build<String, Fluid>()

    @JvmStatic
    fun addMatItemToTag(obj: IMaterialObject) {
        when (obj) {
            is Item -> {
                addItemToTag(obj.objType.name + "s/" + obj.mat.name, obj)
                addItemToTag(obj.objType.name + "s", obj)
                if (obj.mat.hasSecondName)
                    addItemToTag(obj.objType.name + "s/" + obj.mat.secondName, obj)
            }
            is Block -> {
                addBlockToTag(obj.objType.name + "s/" + obj.mat.name, obj)
                addBlockToTag(obj.objType.name + "s", obj)
                if (obj.mat.hasSecondName)
                    addBlockToTag(obj.objType.name + "s/" + obj.mat.secondName, obj)
            }
            is Fluid -> {
                addFluidToTag((if (obj.mat !is FluidMaterial) obj.objType.name + "_" else "") + obj.mat.name, obj)
                if (obj.mat.hasSecondName)
                    addFluidToTag((if (obj.mat !is FluidMaterial) obj.objType.name + "_" else "") + obj.mat.secondName, obj)
            }
        }
    }

    @JvmStatic
    fun addItemToTag(tag: String, item: Item) = ITEM_TAGS.put(tag, item)

    @JvmStatic
    fun addBlockToTag(tag: String, block: Block) = BLOCK_TAGS.put(tag, block)

    @JvmStatic
    fun addFluidToTag(tag: String, fluid: Fluid) = FLUID_TAGS.put(tag, fluid)
}