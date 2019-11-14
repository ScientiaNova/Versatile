package com.emosewapixel.pixellib.machines.capabilities

import com.emosewapixel.pixellib.extensions.nbt
import com.emosewapixel.pixellib.extensions.times
import com.emosewapixel.pixellib.extensions.toNoNullList
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.NonNullList
import net.minecraftforge.common.util.Constants
import net.minecraftforge.common.util.INBTSerializable
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.IFluidHandler

open class FluidStackHandler @JvmOverloads constructor(val count: Int, val capacity: Int = 10000, val noOutputTanks: Array<Int> = emptyArray(), val noInputTanks: Array<Int> = emptyArray()) : IFluidHandlerModifiable, INBTSerializable<CompoundNBT> {
    constructor(tanks: Int, capacity: Int = 10000, noOutput: IntRange, noInput: IntRange) : this(tanks, capacity, noOutput.toList().toTypedArray(), noInput.toList().toTypedArray())

    constructor(capacity: Int = 10000, inputCount: Int, outputCount: Int) : this(inputCount + outputCount, capacity, 0 until inputCount, inputCount until inputCount + outputCount)

    var tanks = NonNullList.withSize(count, FluidStack.EMPTY)
        set(value) {
            if (tanks.size == value.size)
                field = value
        }

    val inputTanks get() = tanks.filterIndexed { index, _ -> index !in noInputTanks }

    val outputTanks get() = tanks.filterIndexed { index, _ -> index !in noOutputTanks }

    override fun drain(resource: FluidStack?, action: IFluidHandler.FluidAction): FluidStack {
        if (resource == null || resource.isEmpty)
            return FluidStack.EMPTY

        val result = resource.copy()
        outputTanks.filter { it.isFluidEqual(resource) }.forEach {
            val take = it.amount.coerceAtMost(result.amount)
            it.amount -= take
            result.amount -= take
        }
        return result
    }

    override fun drain(maxDrain: Int, action: IFluidHandler.FluidAction): FluidStack {
        val filledTanks = outputTanks.filterNot(FluidStack::isEmpty)
        if (filledTanks.isEmpty()) return FluidStack.EMPTY
        val takeFirst = filledTanks.first().amount.coerceAtMost(maxDrain)
        filledTanks[0].amount -= takeFirst
        val result = FluidStack(filledTanks.first().fluid, maxDrain - takeFirst)
        if (!result.isEmpty)
            filledTanks.filter { it.isFluidEqual(result) }.forEach {
                val take = it.amount.coerceAtMost(result.amount)
                it.amount -= take
                result.amount -= take
            }
        return result
    }

    override fun getTankCapacity(tank: Int) = capacity

    override fun fill(resource: FluidStack?, action: IFluidHandler.FluidAction?): Int {
        if (resource == null || resource.isEmpty)
            return 0
        val consuming = resource.copy()
        tanks.withIndex().filter { it.index !in noInputTanks && isFluidValid(it.index, resource) && (it.value.isEmpty || it.value.fluid == resource.fluid) }.forEach {
            val filled = (getTankCapacity(it.index) - it.value.amount).coerceAtMost(consuming.amount)
            if (it.value.isEmpty) tanks[it.index] = consuming.fluid * filled
            else it.value.amount += filled
            consuming.amount -= filled
        }
        return resource.amount - consuming.amount
    }

    override fun getFluidInTank(tank: Int) = tanks[tank]

    override fun setFluidInTank(tank: Int, stack: FluidStack) {
        tanks[tank] = stack
    }

    override fun getTanks() = count

    override fun isFluidValid(tank: Int, stack: FluidStack) = tank !in noInputTanks

    override fun serializeNBT() = nbt {
        "Fluids" to tanks.mapIndexed { index, tank ->
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
    }

    fun copy() = FluidStackHandler(count, capacity, noOutputTanks, noInputTanks).apply { tanks = this@FluidStackHandler.tanks.map(FluidStack::copy).toNoNullList() }
}