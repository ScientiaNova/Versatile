package com.emosewapixel.pixellib.machines.properties.implementations.primitives.integers

import com.emosewapixel.pixellib.machines.BaseTileEntity

class ClearableIntegerProperty(id: String, te: BaseTileEntity) : TEIntegerProperty(id, te) {
    override fun clear() {
        value = 0
    }

    override fun createDefault() = ClearableIntegerProperty(id, te)
}