package com.scientianovateam.versatile.machines.capabilities.fluids

import com.scientianovateam.versatile.common.extensions.isNotEmpty
import com.scientianovateam.versatile.common.extensions.nbt
import com.scientianovateam.versatile.common.extensions.times
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.NonNullList
import net.minecraftforge.common.util.Constants
import net.minecraftforge.common.util.INBTSerializable
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.IFluidHandler

open class FluidStackHandler @JvmOverloads constructor(val count: Int, val capacity: Int = 10_000) : IFluidHandlerModifiable, INBTSerializable<CompoundNBT> {
    protected var tanks: NonNullList<FluidStack> = NonNullList.withSize(count, FluidStack.EMPTY)

    override fun drain(resource: FluidStack?, action: IFluidHandler.FluidAction): FluidStack {
        if (resource == null || resource.isEmpty)
            return FluidStack.EMPTY

        val consuming = resource.copy()
        val updatedIndices = mutableListOf<Int>()
        tanks.withIndex().filter { it.value.isFluidEqual(resource) }.forEach { (index, tank) ->
            val take = tank.amount.coerceAtMost(consuming.amount)
            if (action == IFluidHandler.FluidAction.EXECUTE) {
                tank.amount -= take
                updatedIndices += index
            }
            consuming.amount -= take
        }
        if (updatedIndices.isNotEmpty()) onContentsChanged(updatedIndices)
        return resource.fluid * (resource.amount - consuming.amount)
    }

    override fun drain(maxDrain: Int, action: IFluidHandler.FluidAction): FluidStack {
        val filledTanks = tanks.withIndex().filterNot { it.value.isEmpty }
        if (filledTanks.isEmpty()) return FluidStack.EMPTY
        val takeFirst = filledTanks.first().value.amount.coerceAtMost(maxDrain)
        filledTanks[0].value.amount -= takeFirst
        val consuming = FluidStack(filledTanks.first().value.fluid, maxDrain - takeFirst)
        val updatedIndices = mutableListOf<Int>()
        if (!consuming.isEmpty)
            filledTanks.filter { it.value.isFluidEqual(consuming) }.forEach { (index, tank) ->
                val take = tank.amount.coerceAtMost(consuming.amount)
                if (action == IFluidHandler.FluidAction.EXECUTE) {
                    tank.amount -= take
                    updatedIndices += index
                }
                consuming.amount -= take
            }
        if (updatedIndices.isNotEmpty()) onContentsChanged(updatedIndices)
        return consuming.fluid * (maxDrain - consuming.amount)
    }

    override fun getTankCapacity(tank: Int) = capacity

    override fun fill(resource: FluidStack?, action: IFluidHandler.FluidAction?): Int {
        if (resource == null || resource.isEmpty)
            return 0
        val consuming = resource.copy()
        val updatedIndices = mutableListOf<Int>()
        tanks.withIndex().filter { isFluidValid(it.index, resource) && (it.value.isEmpty || it.value.fluid == resource.fluid) }.forEach { (index, tank) ->
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

    override fun getFluidInTank(tank: Int) = tanks[tank]

    override fun setFluidInTank(tank: Int, stack: FluidStack) {
        tanks[tank] = stack
        onContentsChanged(listOf(tank))
    }

    override fun getTanks() = count

    override fun isFluidValid(tank: Int, stack: FluidStack) = true

    override fun serializeNBT() = nbt {
        "Fluids" to tanks.withIndex().filter { it.value.isNotEmpty }.map { (index, tank) ->
            nbt {
                "Tank" to index
                tank.writeToNBT(this.result)
            }
        }
        "Size" to tanks.size
    }

    override fun deserializeNBT(nbt: CompoundNBT) {
        if (nbt.contains("Size")) tanks = NonNullList.withSize(nbt.getInt("Size"), FluidStack.EMPTY)
        nbt.getList("Fluids", Constants.NBT.TAG_COMPOUND).forEach {
            val tankId = (it as CompoundNBT).getInt("Tank")

            if (tankId >= 0 && tankId < tanks.size)
                tanks[tankId] = FluidStack.loadFluidStackFromNBT(it)
        }
        onLoad()
    }

    open fun onContentsChanged(indices: List<Int>) {}

    open fun onLoad() {}
}