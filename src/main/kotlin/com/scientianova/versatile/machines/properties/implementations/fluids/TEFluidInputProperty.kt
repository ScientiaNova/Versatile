package com.scientianova.versatile.machines.properties.implementations.fluids

import com.scientianova.versatile.common.extensions.get
import com.scientianova.versatile.common.extensions.set
import com.scientianova.versatile.machines.BaseTileEntity
import com.scientianova.versatile.machines.capabilities.fluids.InputFluidStackHandler

open class TEFluidInputProperty(override val value: InputFluidStackHandler, id: String, te: BaseTileEntity) : TEFluidInventoryProperty(value, id, te) {
    constructor(tanks: Int, id: String, te: BaseTileEntity, capacity: Int = 10_000) : this(object : InputFluidStackHandler(tanks, capacity) {
        override fun onContentsChanged(indices: List<Int>) {
            te.markDirty()
            te.update()
        }
    }, id, te)

    override fun clone(): TEFluidInputProperty {
        val stackHandler = InputFluidStackHandler(value.count, value.capacity)
        (0 until value.tanks).forEach { stackHandler[it] = value[it].copy() }
        return TEFluidInputProperty(stackHandler, id, te)
    }
}