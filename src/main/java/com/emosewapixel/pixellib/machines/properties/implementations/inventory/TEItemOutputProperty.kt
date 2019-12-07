package com.emosewapixel.pixellib.machines.properties.implementations.inventory

import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.capabilities.items.OutputItemStackHandler

open class TEItemOutputProperty(value: OutputItemStackHandler, id: String, te: BaseTileEntity) : TEItemInventoryProperty(value, id, te) {
    constructor(slots: Int, id: String, te: BaseTileEntity) : this(object : OutputItemStackHandler(slots) {
        override fun onContentsChanged(slot: Int) = te.update()
    }, id, te)

    override fun createDefault() = TEItemOutputProperty(OutputItemStackHandler(value.slots), id, te)
}