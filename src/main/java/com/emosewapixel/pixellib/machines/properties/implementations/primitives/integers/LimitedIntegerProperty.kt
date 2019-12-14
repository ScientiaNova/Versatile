package com.emosewapixel.pixellib.machines.properties.implementations.primitives.integers

import com.emosewapixel.pixellib.machines.properties.ILimitedIntegerProperty

open class LimitedIntegerProperty(override val min: Int, override val max: Int) : ILimitedIntegerProperty {
    override fun setValue(new: Int, causeUpdate: Boolean) {
        value = when {
            new > max -> max
            new < min -> min
            else -> new
        }
    }

    override var value = min
        protected set

    override fun createDefault() = LimitedIntegerProperty(min, max)
}