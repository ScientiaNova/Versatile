package com.emosewapixel.pixellib.machines.recipes.utility

import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidStack

open class MachineInput(val items: Collection<ItemStack> = emptyList(), val fluids: Collection<FluidStack> = emptyList())