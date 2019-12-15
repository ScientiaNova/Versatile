package com.emosewapixel.pixellib.machines.properties.implementations.energy

import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.capabilities.energy.OutputEnergyHandler

open class TEEnergyOutputProperty(value: OutputEnergyHandler, id: String, te: BaseTileEntity) : TEEnergyProperty(value, id, te) {
    constructor(capacity: Int, id: String, te: BaseTileEntity) : this(object : OutputEnergyHandler(capacity) {
        override fun onUpdate() {
            te.update()
            te.markDirty()
        }
    }, id, te)

    override fun clone() = TEEnergyOutputProperty(OutputEnergyHandler(value.maxEnergyStored), id, te)
}