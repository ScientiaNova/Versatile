package com.scientianovateam.versatile.machines.properties.implementations.strings

import com.scientianovateam.versatile.machines.properties.IVariableProperty

open class VariableStringProperty(value: String = "") : IVariableProperty<String> {
    override var value = value
        protected set

    override fun clone() = VariableStringProperty(value)

    override fun setValue(new: String, causeUpdate: Boolean) {
        value = new
    }
}