package com.emosewapixel.pixellib.machines.properties

interface IVariableProperty<T> : IValueProperty<T> {
    fun setValue(new: T, causeUpdate: Boolean = true)
}