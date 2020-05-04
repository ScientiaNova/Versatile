package com.scientianova.versatile.items

import com.scientianova.versatile.Versatile
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

//Self Container Items are items that don't get used up in crafting recipes
open class SelfContainerItem(name: String) : Item(Properties().group(_root_ide_package_.com.scientianova.versatile.Versatile.MAIN)) {
    init {
        setRegistryName(name)
    }

    override fun hasContainerItem(stack: ItemStack?) = true

    override fun getContainerItem(itemStack: ItemStack) = ItemStack(this)
}