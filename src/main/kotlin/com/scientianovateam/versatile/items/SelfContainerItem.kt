package com.scientianovateam.versatile.items

import com.scientianovateam.versatile.Versatile
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

//Self Container Items are items that don't get used up in crafting recipes
open class SelfContainerItem(name: String) : Item(Properties().group(Versatile.MAIN)) {
    init {
        setRegistryName(name)
    }

    override fun hasContainerItem(stack: ItemStack?) = true

    override fun getContainerItem(itemStack: ItemStack) = ItemStack(this)
}