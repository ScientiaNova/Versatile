package com.emosewapixel.pixellib.machines.properties.implementations.fluids

import com.emosewapixel.pixellib.extensions.get
import com.emosewapixel.pixellib.extensions.set
import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.capabilities.fluids.OutputFluidStackHandler

open class TEFluidOutputProperty(override val value: OutputFluidStackHandler, id: String, te: BaseTileEntity) : TEFluidInventoryProperty(value, id, te) {
    constructor(tanks: Int, id: String, te: BaseTileEntity, capacity: Int = 10_000) : this(object : OutputFluidStackHandler(tanks, capacity) {
        override fun onContentsChanged(indices: List<Int>) {
            te.markDirty()
            te.update()
        }
    }, id, te)

    override fun clone(): TEFluidOutputProperty {
        val stackHandler = OutputFluidStackHandler(value.count, value.capacity)
        (0 until value.tanks).forEach { stackHandler[it] = value[it].copy() }
        return TEFluidOutputProperty(stackHandler, id, te)
    }
}