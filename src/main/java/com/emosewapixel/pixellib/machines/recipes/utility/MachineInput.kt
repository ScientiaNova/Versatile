package com.emosewapixel.pixellib.machines.recipes.utility

import com.emosewapixel.pixellib.machines.capabilities.FluidStackHandler
import com.emosewapixel.pixellib.machines.capabilities.ImprovedItemStackHandler
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.IFluidTank

open class MachineInput(val items: Collection<ItemStack>, val fluids: Collection<FluidStack>) {
    constructor(itemHandler: ImprovedItemStackHandler, fluidHandler: FluidStackHandler) : this(itemHandler.stacks.filter { !it.isEmpty }, fluidHandler.tanks.map(IFluidTank::getFluid).filter { !it.isEmpty })
}