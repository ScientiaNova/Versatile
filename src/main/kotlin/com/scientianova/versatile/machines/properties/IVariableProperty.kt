package com.scientianova.versatile.machines.properties

interface IVariableProperty<T> : IValueProperty<T> {
    fun setValue(new: T, causeUpdate: Boolean = true)
}