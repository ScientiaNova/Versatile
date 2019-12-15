package com.emosewapixel.pixellib.machines.properties.implementations.energy

import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.capabilities.energy.InputEnergyHandler

open class TEEnergyInputProperty(value: InputEnergyHandler, id: String, te: BaseTileEntity) : TEEnergyProperty(value, id, te) {
    constructor(capacity: Int, id: String, te: BaseTileEntity) : this(object : InputEnergyHandler(capacity) {
        override fun onUpdate() {
            te.update()
            te.markDirty()
        }
    }, id, te)

    override fun clone() = TEEnergyInputProperty(InputEnergyHandler(value.maxEnergyStored), id, te)
}