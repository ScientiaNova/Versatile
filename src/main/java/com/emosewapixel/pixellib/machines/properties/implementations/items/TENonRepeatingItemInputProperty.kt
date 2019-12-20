package com.emosewapixel.pixellib.machines.properties.implementations.items

import com.emosewapixel.pixellib.extensions.get
import com.emosewapixel.pixellib.extensions.set
import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.capabilities.items.NonRepeatingInputItemStackHandler

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