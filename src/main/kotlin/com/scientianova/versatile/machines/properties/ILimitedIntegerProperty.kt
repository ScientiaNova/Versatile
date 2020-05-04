package com.scientianova.versatile.machines.properties

interface ILimitedIntegerProperty : IVariableProperty<Int> {
    val min: Int
    val max: Int

    operator fun inc(): ILimitedIntegerProperty {
        setValue(if (value + 1 > max) min else value + 1)
        return this
    }

    operator fun dec(): ILimitedIntegerProperty {
        setValue(if (value - 1 < min) max else value - 1)
        return this
    }
}