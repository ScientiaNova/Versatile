package com.emosewapixel.pixellib.machines.properties.implementations.primitives.integers

import com.emosewapixel.pixellib.machines.BaseTileEntity

class TEClearableIntegerProperty(id: String, te: BaseTileEntity) : TEIntegerProperty(id, te) {
    override fun clear() {
        value = 0
    }

    override fun clone() = TEClearableIntegerProperty(id, te)
}