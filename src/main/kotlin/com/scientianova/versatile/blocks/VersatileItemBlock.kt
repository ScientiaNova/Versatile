package com.scientianova.versatile.blocks

import com.scientianova.versatile.items.ExtendedItemProperties
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.util.text.ITextComponent
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class VersatileBlockItem(block: Block, properties: ExtendedItemProperties) : BlockItem(block, properties) {
    private val burnTime = properties.burnTime
    private val destroySpeed = properties.destroySpeed
    private val enchantable: Boolean = properties.enchantable
    private val enchantability = properties.enchantability
    private val hasEffect = properties.hasEffect

    @OnlyIn(Dist.CLIENT)
    override fun getDisplayName(stack: ItemStack): ITextComponent = block.nameTextComponent
    override fun getBurnTime(itemStack: ItemStack?) = burnTime
    override fun getDestroySpeed(stack: ItemStack, state: BlockState) = destroySpeed
    override fun isEnchantable(stack: ItemStack) = enchantable
    override fun getItemEnchantability(stack: ItemStack?) = enchantability
    override fun hasEffect(stack: ItemStack) = hasEffect || super.hasEffect(stack)
}