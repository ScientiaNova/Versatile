package com.scientianovateam.versatile.machines.properties.implementations.primitives.integers

import com.scientianovateam.versatile.machines.properties.IValueProperty

open class ConstantIntegerProperty(override val value: Int) : IValueProperty<Int> {
    override fun clone() = ConstantIntegerProperty(value)
}