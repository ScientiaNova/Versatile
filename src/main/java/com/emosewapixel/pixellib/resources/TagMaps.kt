package com.emosewapixel.pixellib.resources

import com.emosewapixel.pixellib.materialsystem.materials.IMaterialItem
import com.google.common.collect.ListMultimap
import com.google.common.collect.MultimapBuilder
import net.minecraft.block.Block
import net.minecraft.item.Item

//This class contains functions used for adding Item and Block Tags to the fake data pack
object TagMaps {
    @JvmField
    val ITEM_TAGS: ListMultimap<String, Item> = MultimapBuilder.treeKeys().arrayListValues().build<String, Item>()

    @JvmField
    val BLOCK_TAGS: ListMultimap<String, Block> = MultimapBuilder.treeKeys().arrayListValues().build<String, Block>()

    @JvmStatic
    fun addMatItemToTag(item: IMaterialItem) {
        if (item is Item) {
            addItemToTag(item.objType.name + "s/" + item.mat.name, item as Item)
            addItemToTag(item.objType.name + "s", item as Item)
            if (item.mat.hasSecondName())
                addItemToTag(item.objType.name + "s/" + item.mat.secondName, item as Item)
        }
        if (item is Block) {
            addBlockToTag(item.objType.name + "s/" + item.mat.name, item as Block)
            addBlockToTag(item.objType.name + "s", item as Block)
            if (item.mat.hasSecondName())
                addBlockToTag(item.objType.name + "s/" + item.mat.secondName, item as Block)
        }
    }

    @JvmStatic
    fun addItemToTag(tag: String, item: Item) {
        ITEM_TAGS.put(tag, item)
    }

    @JvmStatic
    fun addBlockToTag(tag: String, block: Block) {
        BLOCK_TAGS.put(tag, block)
        ITEM_TAGS.put(tag, Item.getItemFromBlock(block))
    }
}