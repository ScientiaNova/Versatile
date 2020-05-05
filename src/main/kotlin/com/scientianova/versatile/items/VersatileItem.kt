package com.scientianova.versatile.items

import net.minecraft.block.BlockState
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class VersatileItem(properties: ExtendedItemProperties) : Item(properties) {
    private val localizedNameFun = properties.localizedNameFun
    private val burnTime = properties.burnTime
    private val destroySpeed = properties.destroySpeed
    private val enchantable: Boolean = properties.enchantable
    private val enchantability = properties.enchantability
    private val hasEffect = properties.hasEffect

    @OnlyIn(Dist.CLIENT)
    override fun getDisplayName(stack: ItemStack) = localizedNameFun()
    override fun getBurnTime(itemStack: ItemStack?) = burnTime
    override fun getDestroySpeed(stack: ItemStack, state: BlockState) = destroySpeed
    override fun isEnchantable(stack: ItemStack) = enchantable
    override fun getItemEnchantability(stack: ItemStack?) = enchantability
    override fun hasEffect(stack: ItemStack) = hasEffect || super.hasEffect(stack)
}