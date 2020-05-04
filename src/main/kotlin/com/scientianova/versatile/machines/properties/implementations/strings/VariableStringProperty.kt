package com.scientianova.versatile.machines.properties.implementations.strings

import com.scientianova.versatile.machines.properties.IVariableProperty

open class VariableStringProperty(value: String = "") : IVariableProperty<String> {
    override var value = value
        protected set

    override fun clone() = VariableStringProperty(value)

    override fun setValue(new: String, causeUpdate: Boolean) {
        value = new
    }
}