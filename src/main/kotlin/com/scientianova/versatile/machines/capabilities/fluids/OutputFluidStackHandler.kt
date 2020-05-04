package com.scientianova.versatile.machines.capabilities.fluids

import com.scientianova.versatile.common.extensions.times
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.IFluidHandler

open class OutputFluidStackHandler(count: Int, capacity: Int) : FluidStackHandler(count, capacity), IContainerFluidHandler {
    override fun isFluidValid(tank: Int, stack: FluidStack) = false

    override fun forceFill(resource: FluidStack, action: IFluidHandler.FluidAction): Int {
        if (resource.isEmpty) return 0
        val consuming = resource.copy()
        val updatedIndices = mutableListOf<Int>()
        tanks.withIndex().filter { it.value.isEmpty || it.value.fluid == resource.fluid }.forEach { (index, tank) ->
            val filled = (getTankCapacity(index) - tank.amount).coerceAtMost(consuming.amount)
            if (action == IFluidHandler.FluidAction.EXECUTE) {
                if (tank.isEmpty) tanks[index] = consuming.fluid * filled
                else tank.amount += filled
                updatedIndices += index
            }
            consuming.amount -= filled
            if (consuming.amount == 0) return resource.amount
        }
        if (updatedIndices.isNotEmpty()) onContentsChanged(updatedIndices)
        return resource.amount - consuming.amount
    }
}