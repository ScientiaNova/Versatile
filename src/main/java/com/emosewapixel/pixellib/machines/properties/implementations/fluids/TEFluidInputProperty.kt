package com.emosewapixel.pixellib.machines.properties.implementations.fluids

import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.capabilities.fluids.InputFluidStackHandler

open class TEFluidInputProperty(value: InputFluidStackHandler, id: String, te: BaseTileEntity) : TEFluidInventoryProperty(value, id, te) {
    constructor(tanks: Int, id: String, te: BaseTileEntity, capacity: Int = 10_000) : this(object : InputFluidStackHandler(tanks, capacity) {
        override fun onContentsChanged(index: Int) {
            te.markDirty()
            te.update()
        }
    }, id, te)

    override fun createDefault() = TEFluidInputProperty(InputFluidStackHandler(value.tanks, value.capacity), id, te)
}