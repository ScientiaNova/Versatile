package com.scientianova.versatile.machines.properties.implementations.primitives.doubles

import com.scientianova.versatile.machines.properties.IValueProperty

open class IncrementingDoubleProperty(private val increment: Double = 0.01) : IValueProperty<Double> {
    private var lastAccessed = System.currentTimeMillis()

    override var value = 0.0
        get() {
            if (System.currentTimeMillis() >= lastAccessed + 48) {
                lastAccessed = System.currentTimeMillis()
                field += increment
                if (field > 1) field = 0.0
            }
            return field
        }
        protected set

    override fun clone() = IncrementingDoubleProperty(increment)
}