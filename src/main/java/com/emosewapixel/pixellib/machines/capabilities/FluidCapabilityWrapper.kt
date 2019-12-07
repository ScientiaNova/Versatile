package com.emosewapixel.pixellib.machines.capabilities

import com.emosewapixel.pixellib.extensions.times
import net.minecraft.fluid.Fluids
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.fluids.capability.IFluidHandler

open class FluidCapabilityWrapper : IFluidHandlerModifiable, ICapabilityProvider {
    protected val handlers = arrayListOf<IFluidHandler>()

    fun addHandler(handler: IFluidHandler) = handlers.add(handler)

    val handlerTankCounts get() = handlers.map(IFluidHandler::getTanks)

    override fun getTanks() = handlerTankCounts.sum()

    fun getTankAndHandlerFromIndex(index: Int): Pair<Int, Int> {
        var indexInCurrent = index
        handlers.forEachIndexed { i, handler -> if (handler.tanks >= indexInCurrent) return i to indexInCurrent else indexInCurrent -= handler.tanks }
        throw IndexOutOfBoundsException()
    }

    override fun getFluidInTank(tank: Int): FluidStack {
        val (handlerIndex, index) = getTankAndHandlerFromIndex(tank)
        return handlers[handlerIndex].getFluidInTank(index)
    }

    override fun getTankCapacity(tank: Int): Int {
        val (handlerIndex, index) = getTankAndHandlerFromIndex(tank)
        return handlers[handlerIndex].getTankCapacity(index)
    }

    override fun isFluidValid(tank: Int, stack: FluidStack): Boolean {
        val (handlerIndex, index) = getTankAndHandlerFromIndex(tank)
        return handlers[handlerIndex].isFluidValid(index, stack)
    }

    override fun setFluidInTank(tank: Int, stack: FluidStack) {
        val (handlerIndex, index) = getTankAndHandlerFromIndex(tank)
        (handlers[handlerIndex] as? IFluidHandlerModifiable)?.setFluidInTank(index, stack)
    }

    override fun drain(maxAmount: Int, action: IFluidHandler.FluidAction?): FluidStack = handlers.fold(Fluids.EMPTY * maxAmount) { acc, handler ->
        if (acc.isEmpty) handler.drain(acc.amount, action).let { it.fluid * (acc.amount - it.amount) }
        else acc.fluid * (acc.amount - handler.drain(acc, action).amount)
    }.let { it.fluid * (maxAmount - it.amount) }

    override fun drain(resource: FluidStack?, action: IFluidHandler.FluidAction?): FluidStack =
            if (resource == null) FluidStack.EMPTY else resource.fluid * (resource.amount - handlers.fold(resource.copy()) { acc, handler -> acc.fluid * (acc.amount - handler.drain(acc, action).amount) }.amount)

    override fun fill(resource: FluidStack?, action: IFluidHandler.FluidAction?): Int =
            if (resource == null) 0 else resource.amount - handlers.fold(resource.copy()) { acc, handler -> acc.fluid * (acc.amount - handler.fill(acc, action)) }.amount

    private val optional = LazyOptional.of { this }

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> =
            if (cap === CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) optional.cast() else LazyOptional.empty()
}