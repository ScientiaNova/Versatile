package com.scientianova.versatile.machines.properties

interface IValueProperty<T> : IMachineProperty {
    val value: T
}