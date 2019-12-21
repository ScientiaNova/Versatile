package com.emosewapixel.pixellib.machines.properties.implementations.strings

import com.emosewapixel.pixellib.machines.properties.IVariableProperty

open class VariableStringProperty(value: String = "") : IVariableProperty<String> {
    override var value = value
        protected set

    override fun clone() = VariableStringProperty(value)

    override fun setValue(new: String, causeUpdate: Boolean) {
        value = new
    }
}