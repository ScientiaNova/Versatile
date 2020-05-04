package com.scientianova.versatile.machines.properties.implementations.strings

import com.scientianova.versatile.machines.properties.IValueProperty

open class ConstantStringProperty(override val value: String) : IValueProperty<String> {
    override fun clone() = ConstantStringProperty(value)
}