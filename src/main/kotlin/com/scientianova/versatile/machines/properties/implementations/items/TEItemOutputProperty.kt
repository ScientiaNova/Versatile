package com.scientianova.versatile.machines.properties.implementations.items

import com.scientianovateam.versatile.common.extensions.get
import com.scientianovateam.versatile.common.extensions.set
import com.scientianovateam.versatile.machines.BaseTileEntity
import com.scientianovateam.versatile.machines.capabilities.items.OutputItemStackHandler

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