package com.emosewapixel.pixellib.machines.capabilities

import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.IFluidTank
import net.minecraftforge.fluids.capability.IFluidHandler

class FluidStackHandler @JvmOverloads constructor(val tanks: List<IFluidTank>, val noOutputTanks: Array<Int> = emptyArray(), val noInputTanks: Array<Int> = emptyArray()) : IFluidHandler {
    val inputTanks: List<IFluidTank>
        get() = tanks.filterIndexed { index, _ -> index !in noInputTanks }

    val outputTanks: List<IFluidTank>
        get() = tanks.filterIndexed { index, _ -> index !in noOutputTanks }

    constructor(tanks: List<IFluidTank>, noOutput: IntRange, noInput: IntRange) : this(tanks, noOutput.toList().toTypedArray(), noInput.toList().toTypedArray())

    override fun drain(resource: FluidStack?, action: IFluidHandler.FluidAction): FluidStack {
        if (resource == null || resource.isEmpty)
            return FluidStack.EMPTY

        val result = resource.copy()
        outputTanks.filter { it.fluid.isFluidEqual(resource) }.reversed().forEach {
            result.amount -= it.drain(result, action).amount
        }
        return result
    }

    override fun drain(maxDrain: Int, action: IFluidHandler.FluidAction): FluidStack {
        val filledTanks = outputTanks.filter { !it.fluid.isEmpty }.reversed()
        val result = filledTanks[0].drain(maxDrain, action)
        if (!result.isEmpty)
            filledTanks.filter { it.fluid.isFluidEqual(result) }.forEach {
                result.amount -= it.drain(result, action).amount
            }
        return result
    }

    override fun getTankCapacity(tank: Int) = tanks[tank].capacity

    override fun fill(resource: FluidStack?, action: IFluidHandler.FluidAction?): Int {
        if (resource == null || resource.isEmpty)
            return 0
        val consuming = resource.copy()
        inputTanks.forEach { consuming.amount -= it.fill(consuming, action) }
        return consuming.amount
    }

    override fun getFluidInTank(tank: Int) = tanks[tank].fluid

    override fun getTanks() = tanks.size

    override fun isFluidValid(tank: Int, stack: FluidStack) = inputTanks.any { it.isFluidValid(stack) }
}