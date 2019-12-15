package com.emosewapixel.pixellib.machines.properties.implementations.items

import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.capabilities.items.InputItemStackHandler

open class TEItemInputProperty(value: InputItemStackHandler, id: String, te: BaseTileEntity) : TEItemInventoryProperty(value, id, te) {
    constructor(slots: Int, id: String, te: BaseTileEntity) : this(object : InputItemStackHandler(slots) {
        override fun onContentsChanged(slot: Int) {
            te.markDirty()
            te.update()
        }
    }, id, te)

    override fun clone() = TEItemInputProperty(InputItemStackHandler(value.slots), id, te)
}