package com.scientianova.versatile.fluids

import com.scientianova.versatile.items.ExtendedItemProperties
import net.minecraft.block.BlockState
import net.minecraft.fluid.Fluid
import net.minecraft.item.BucketItem
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper

open class VersatileBucketItem(fluid: () -> Fluid, properties: ExtendedItemProperties) : BucketItem(fluid, properties) {
    private val burnTime = properties.burnTime
    private val destroySpeed = properties.destroySpeed
    private val enchantable: Boolean = properties.enchantable
    private val enchantability = properties.enchantability
    private val hasEffect = properties.hasEffect

    override fun getBurnTime(itemStack: ItemStack?) = burnTime
    override fun getDestroySpeed(stack: ItemStack, state: BlockState) = destroySpeed
    override fun isEnchantable(stack: ItemStack) = enchantable
    override fun getItemEnchantability(stack: ItemStack?) = enchantability
    override fun hasEffect(stack: ItemStack) = hasEffect || super.hasEffect(stack)

    @OnlyIn(Dist.CLIENT)
    override fun getDisplayName(stack: ItemStack) =
            TranslationTextComponent("container.bucket", fluid.attributes.getDisplayName(FluidStack.EMPTY))

    override fun initCapabilities(stack: ItemStack, nbt: CompoundNBT?) = FluidBucketWrapper(stack)
}