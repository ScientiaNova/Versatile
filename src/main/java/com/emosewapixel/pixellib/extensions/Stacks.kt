package com.emosewapixel.pixellib.extensions

import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidStack

operator fun ItemStack.component1(): Item = item
operator fun ItemStack.component2() = count

operator fun FluidStack.component1(): Fluid = fluid
operator fun FluidStack.component2() = amount