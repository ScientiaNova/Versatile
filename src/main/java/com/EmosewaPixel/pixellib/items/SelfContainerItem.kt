package com.EmosewaPixel.pixellib.items

import com.EmosewaPixel.pixellib.PixelLib
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

//Self Container Items are items that don't get used up in crafting recipes
class SelfContainerItem(name: String) : Item(Item.Properties().group(PixelLib.main)) {
    init {
        setRegistryName(name)
    }

    override fun hasContainerItem(stack: ItemStack?): Boolean {
        return true
    }

    override fun getContainerItem(itemStack: ItemStack): ItemStack {
        return ItemStack(this)
    }
}