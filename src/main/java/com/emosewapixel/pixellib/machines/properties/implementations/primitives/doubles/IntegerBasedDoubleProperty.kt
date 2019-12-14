package com.emosewapixel.pixellib.machines.properties.implementations.primitives.doubles

import com.emosewapixel.pixellib.machines.properties.IValueProperty

open class IntegerBasedDoubleProperty(val intProperty: IValueProperty<Int>, val max: Int) : IValueProperty<Double> {
    override val value
        get() = intProperty.value / max.toDouble()

    override fun createDefault() = IntegerBasedDoubleProperty(intProperty, max)
}