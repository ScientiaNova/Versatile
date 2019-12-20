package com.emosewapixel.pixellib.machines.properties.implementations.fluids

import com.emosewapixel.pixellib.extensions.get
import com.emosewapixel.pixellib.extensions.set
import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.capabilities.fluids.NonRepeatingInputFluidStackHandler

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