package com.scientianovateam.versatile.machines.properties.implementations.strings

import com.scientianovateam.versatile.machines.properties.IValueProperty

open class ConstantStringProperty(override val value: String) : IValueProperty<String> {
    override fun clone() = ConstantStringProperty(value)
}