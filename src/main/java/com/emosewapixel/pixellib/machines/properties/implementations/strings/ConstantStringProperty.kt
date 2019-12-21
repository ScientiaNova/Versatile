package com.emosewapixel.pixellib.machines.properties.implementations.strings

import com.emosewapixel.pixellib.machines.properties.IValueProperty

open class ConstantStringProperty(override val value: String) : IValueProperty<String> {
    override fun clone() = ConstantStringProperty(value)
}