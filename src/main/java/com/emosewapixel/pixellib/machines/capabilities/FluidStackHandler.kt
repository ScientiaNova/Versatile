package com.emosewapixel.pixellib.machines.capabilities

import com.emosewapixel.pixellib.extensions.isNotEmpty
import com.emosewapixel.pixellib.extensions.nbt
import com.emosewapixel.pixellib.extensions.times
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.NonNullList
import net.minecraftforge.common.util.Constants
import net.minecraftforge.common.util.INBTSerializable
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.IFluidHandler

open class FluidStackHandler @JvmOverloads constructor(val count: Int, val noOutputTanks: Array<Int> = emptyArray(), val noInputTanks: Array<Int> = emptyArray(), val capacity: Int = 10000) : IFluidHandlerModifiable, INBTSerializable<CompoundNBT> {
    @JvmOverloads
    constructor(tanks: Int, noOutput: IntRange, noInput: IntRange, capacity: Int = 10000)
            : this(tanks, noOutput.toList().toTypedArray(), noInput.toList().toTypedArray(), capacity)

    @JvmOverloads
    constructor(inputCount: Int, outputCount: Int, capacity: Int = 10000)
            : this(inputCount + outputCount, 0 until inputCount, inputCount until inputCount + outputCount, capacity)

    constructor(tanks: NonNullList<FluidStack>, noOutputSlots: Array<Int> = emptyArray(), noInputSlots: Array<Int> = emptyArray(), capacity: Int = 10000) : this(tanks.size, noOutputSlots, noInputSlots, capacity) {
        this.tanks = tanks
    }

    var tanks = NonNullList.withSize(count, FluidStack.EMPTY)
        protected set

    val inputTanks get() = tanks.filterIndexed { index, _ -> index !in noInputTanks }

    private val indexedInputTanks get() = tanks.withIndex().filter { it.index !in noInputTanks }

    val outputTanks get() = tanks.filterIndexed { index, _ -> index !in noOutputTanks }

    private val indexedOutputTanks get() = tanks.withIndex().filter { it.index !in noOutputTanks }

    override fun drain(resource: FluidStack?, action: IFluidHandler.FluidAction): FluidStack {
        if (resource == null || resource.isEmpty)
            return FluidStack.EMPTY

        val consuming = resource.copy()
        indexedOutputTanks.filter { it.value.isFluidEqual(resource) }.forEach { (index, tank) ->
            val take = tank.amount.coerceAtMost(consuming.amount)
            if (action == IFluidHandler.FluidAction.EXECUTE) {
                tank.amount -= take
                onContentsChanged(index)
            }
            consuming.amount -= take
        }
        return resource.fluid * (resource.amount - consuming.amount)
    }

    override fun drain(maxDrain: Int, action: IFluidHandler.FluidAction): FluidStack {
        val filledTanks = indexedOutputTanks.filterNot { it.value.isEmpty }
        if (filledTanks.isEmpty()) return FluidStack.EMPTY
        val takeFirst = filledTanks.first().value.amount.coerceAtMost(maxDrain)
        filledTanks[0].value.amount -= takeFirst
        val consuming = FluidStack(filledTanks.first().value.fluid, maxDrain - takeFirst)
        if (!consuming.isEmpty)
            filledTanks.filter { it.value.isFluidEqual(consuming) }.forEach { (index, tank) ->
                val take = tank.amount.coerceAtMost(consuming.amount)
                if (action == IFluidHandler.FluidAction.EXECUTE) {
                    tank.amount -= take
                    onContentsChanged(index)
                }
                consuming.amount -= take
            }
        return consuming.fluid * (maxDrain - consuming.amount)
    }

    override fun getTankCapacity(tank: Int) = capacity

    override fun fill(resource: FluidStack?, action: IFluidHandler.FluidAction?): Int {
        if (resource == null || resource.isEmpty)
            return 0
        val consuming = resource.copy()
        tanks.withIndex().filter { it.index !in noInputTanks && isFluidValid(it.index, resource) && (it.value.isEmpty || it.value.fluid == resource.fluid) }.forEach { (index, tank) ->
            val filled = (getTankCapacity(index) - tank.amount).coerceAtMost(consuming.amount)
            if (action == IFluidHandler.FluidAction.EXECUTE) {
                if (tank.isEmpty) tanks[index] = consuming.fluid * filled
                else tank.amount += filled
                onContentsChanged(index)
            }
            consuming.amount -= filled
            if (consuming.amount == 0) return resource.amount
        }
        return resource.amount - consuming.amount
    }

    override fun getFluidInTank(tank: Int) = tanks[tank]

    override fun setFluidInTank(tank: Int, stack: FluidStack) {
        tanks[tank] = stack
        onContentsChanged(tank)
    }

    override fun getTanks() = count

    override fun isFluidValid(tank: Int, stack: FluidStack) = tank !in noInputTanks

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

    open fun onContentsChanged(index: Int) {}

    open fun onLoad() {}
}