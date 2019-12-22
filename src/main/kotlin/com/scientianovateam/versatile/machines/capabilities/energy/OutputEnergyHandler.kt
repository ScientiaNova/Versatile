package com.scientianovateam.versatile.machines.capabilities.energy

open class OutputEnergyHandler(max: Int) : EnergyHandler(max) {
    override fun canReceive() = false
}