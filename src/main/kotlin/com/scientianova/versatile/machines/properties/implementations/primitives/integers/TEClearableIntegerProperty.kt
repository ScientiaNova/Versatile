package com.scientianova.versatile.machines.properties.implementations.primitives.integers

import com.scientianova.versatile.machines.BaseTileEntity

class TEClearableIntegerProperty(id: String, te: BaseTileEntity) : TEIntegerProperty(id, te) {
    override fun clear() {
        value = 0
    }

    override fun clone() = TEClearableIntegerProperty(id, te)
}