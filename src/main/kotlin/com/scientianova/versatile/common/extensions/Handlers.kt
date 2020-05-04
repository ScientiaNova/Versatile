package com.scientianova.versatile.common.extensions

import com.scientianova.versatile.machines.capabilities.fluids.IFluidHandlerModifiable
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.IFluidHandler
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.IItemHandlerModifiable

operator fun IItemHandler.get(slot: Int) = getStackInSlot(slot)
operator fun IItemHandlerModifiable.set(slot: Int, stack: ItemStack) = setStackInSlot(slot, stack)
fun IItemHandler.toList() = (0 until slots).map(::getStackInSlot).filter(ItemStack::isNotEmpty)

operator fun IFluidHandler.get(tank: Int) = getFluidInTank(tank)
operator fun IFluidHandlerModifiable.set(tank: Int, stack: FluidStack) = setFluidInTank(tank, stack)
fun IFluidHandlerModifiable.toList() = (0 until tanks).map(::getFluidInTank).filter(FluidStack::isNotEmpty)