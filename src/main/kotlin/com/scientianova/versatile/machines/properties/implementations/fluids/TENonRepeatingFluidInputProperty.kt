package com.scientianova.versatile.machines.properties.implementations.fluids

import com.scientianova.versatile.common.extensions.get
import com.scientianova.versatile.common.extensions.set
import com.scientianova.versatile.machines.BaseTileEntity
import com.scientianova.versatile.machines.capabilities.fluids.NonRepeatingInputFluidStackHandler

open class TENonRepeatingFluidInputProperty(override val value: NonRepeatingInputFluidStackHandler, id: String, te: BaseTileEntity) : TEFluidInputProperty(value, id, te) {
    constructor(tanks: Int, id: String, te: BaseTileEntity, capacity: Int = 10_000) : this(object : NonRepeatingInputFluidStackHandler(tanks, capacity) {
        override fun onContentsChanged(indices: List<Int>) {
            te.markDirty()
            te.update()
        }
    }, id, te)

    override fun clone(): TENonRepeatingFluidInputProperty {
        val stackHandler = NonRepeatingInputFluidStackHandler(value.count, value.capacity)
        (0 until value.tanks).forEach { stackHandler[it] = value[it].copy() }
        return TENonRepeatingFluidInputProperty(stackHandler, id, te)
    }
}