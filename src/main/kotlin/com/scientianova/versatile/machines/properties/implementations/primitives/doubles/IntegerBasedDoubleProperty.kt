package com.scientianova.versatile.machines.properties.implementations.primitives.doubles

import com.scientianova.versatile.machines.properties.IValueProperty

open class IntegerBasedDoubleProperty(val intProperty: IValueProperty<Int>, val max: Int) : IValueProperty<Double> {
    override val value get() = intProperty.value / max.toDouble()

    override fun clone() = IntegerBasedDoubleProperty(intProperty, max)
}