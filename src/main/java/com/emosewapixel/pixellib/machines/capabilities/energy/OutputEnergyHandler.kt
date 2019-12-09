package com.emosewapixel.pixellib.machines.capabilities.energy

open class OutputEnergyHandler(max: Int) : EnergyHandler(max) {
    override fun canReceive() = false
}