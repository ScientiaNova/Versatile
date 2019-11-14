package com.emosewapixel.pixellib.machines.recipes.utility

import com.emosewapixel.pixellib.machines.capabilities.FluidStackHandler
import com.emosewapixel.pixellib.machines.capabilities.ImprovedItemStackHandler
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidStack

open class MachineInput(val items: Collection<ItemStack> = emptyList(), val fluids: Collection<FluidStack> = emptyList()) {
    constructor(itemHandler: ImprovedItemStackHandler, fluidHandler: FluidStackHandler) : this(itemHandler.invStacks, fluidHandler.tanks)
}