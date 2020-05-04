package com.scientianova.versatile.common.extensions

import net.minecraft.block.Block
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidStack

operator fun ItemStack.component1(): Item = item
operator fun ItemStack.component2() = count
val ItemStack.isNotEmpty get() = !isEmpty

operator fun FluidStack.component1(): Fluid = fluid
operator fun FluidStack.component2() = amount
val FluidStack.isNotEmpty get() = !isEmpty


fun Item.toStack(count: Int = 1) = ItemStack(this, count)
operator fun Item.times(count: Int) = ItemStack(this, count)

fun Block.toStack(count: Int = 1) = ItemStack(this, count)
operator fun Block.times(count: Int) = ItemStack(this, count)

fun Fluid.toStack(amount: Int = 1000) = FluidStack(this, amount)
operator fun Fluid.times(count: Int) = FluidStack(this, count)