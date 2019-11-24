package com.emosewapixel.pixellib.items

import com.emosewapixel.pixellib.PixelLib
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

//Self Container Items are items that don't get used up in crafting recipes
open class SelfContainerItem(name: String) : Item(Properties().group(PixelLib.MAIN)) {
    init {
        setRegistryName(name)
    }

    override fun hasContainerItem(stack: ItemStack?) = true

    override fun getContainerItem(itemStack: ItemStack) = ItemStack(this)
}