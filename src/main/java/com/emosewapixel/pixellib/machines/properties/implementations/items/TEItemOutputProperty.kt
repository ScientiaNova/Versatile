package com.emosewapixel.pixellib.machines.properties.implementations.items

import com.emosewapixel.pixellib.extensions.get
import com.emosewapixel.pixellib.extensions.set
import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.capabilities.items.OutputItemStackHandler

open class TEItemOutputProperty(override val value: OutputItemStackHandler, id: String, te: BaseTileEntity) : TEItemInventoryProperty(value, id, te) {
    constructor(slots: Int, id: String, te: BaseTileEntity) : this(object : OutputItemStackHandler(slots) {
        override fun onContentsChanged(slot: Int) {
            te.markDirty()
            te.update()
        }
    }, id, te)

    override fun clone(): TEItemOutputProperty {
        val stackHandler = OutputItemStackHandler(value.slots)
        (0 until value.slots).forEach { stackHandler[it] = value[it].copy() }
        return TEItemOutputProperty(stackHandler, id, te)
    }
}