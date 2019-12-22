package com.scientianovateam.versatile.machines.capabilities.energy

open class InputEnergyHandler(max: Int) : EnergyHandler(max) {
    override fun canExtract() = false
}