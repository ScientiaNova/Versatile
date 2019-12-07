package com.emosewapixel.pixellib.extensions

import com.emosewapixel.pixellib.machines.capabilities.fluids.IFluidHandlerModifiable
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.IFluidHandler
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.IItemHandlerModifiable

operator fun IItemHandler.get(slot: Int) = getStackInSlot(slot)
operator fun IItemHandlerModifiable.set(slot: Int, stack: ItemStack) = setStackInSlot(slot, stack)

operator fun IFluidHandler.get(tank: Int) = getFluidInTank(tank)
operator fun IFluidHandlerModifiable.set(tank: Int, stack: FluidStack) = setFluidInTank(tank, stack)