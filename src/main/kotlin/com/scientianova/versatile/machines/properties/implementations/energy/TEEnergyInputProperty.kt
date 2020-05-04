package com.scientianova.versatile.machines.properties.implementations.energy

import com.scientianovateam.versatile.machines.BaseTileEntity
import com.scientianovateam.versatile.machines.capabilities.energy.InputEnergyHandler

open class TEEnergyInputProperty(value: InputEnergyHandler, id: String, te: BaseTileEntity) : TEEnergyProperty(value, id, te) {
    constructor(capacity: Int, id: String, te: BaseTileEntity) : this(object : InputEnergyHandler(capacity) {
        override fun onUpdate() {
            te.update()
            te.markDirty()
        }
    }, id, te)

    override fun clone(): TEEnergyInputProperty {
        val handler = InputEnergyHandler(value.maxEnergyStored)
        handler.energyStored = value.energyStored
        return TEEnergyInputProperty(handler, id, te)
    }
}