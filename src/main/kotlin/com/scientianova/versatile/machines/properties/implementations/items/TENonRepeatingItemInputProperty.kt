package com.scientianova.versatile.machines.properties.implementations.items

import com.scientianovateam.versatile.common.extensions.get
import com.scientianovateam.versatile.common.extensions.set
import com.scientianovateam.versatile.machines.BaseTileEntity
import com.scientianovateam.versatile.machines.capabilities.items.NonRepeatingInputItemStackHandler

open class TENonRepeatingItemInputProperty(value: NonRepeatingInputItemStackHandler, id: String, te: BaseTileEntity) : TEItemInputProperty(value, id, te) {
    constructor(slots: Int, id: String, te: BaseTileEntity) : this(object : NonRepeatingInputItemStackHandler(slots) {
        override fun onContentsChanged(slot: Int) {
            te.markDirty()
            te.update()
        }
    }, id, te)

    override fun clone(): TENonRepeatingItemInputProperty {
        val stackHandler = NonRepeatingInputItemStackHandler(value.slots)
        (0 until value.slots).forEach { stackHandler[it] = value[it].copy() }
        return TENonRepeatingItemInputProperty(stackHandler, id, te)
    }
}