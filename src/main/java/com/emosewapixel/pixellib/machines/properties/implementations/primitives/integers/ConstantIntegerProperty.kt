package com.emosewapixel.pixellib.machines.properties.implementations.primitives.integers

import com.emosewapixel.pixellib.machines.properties.IValueProperty

open class ConstantIntegerProperty(override val value: Int) : IValueProperty<Int> {
    override fun clone() = ConstantIntegerProperty(value)
}