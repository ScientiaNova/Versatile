package com.scientianovateam.versatile.machines.properties

interface IValueProperty<T> : IMachineProperty {
    val value: T
}