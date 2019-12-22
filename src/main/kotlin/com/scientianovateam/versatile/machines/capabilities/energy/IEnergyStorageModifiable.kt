package com.scientianovateam.versatile.machines.capabilities.energy

import net.minecraftforge.energy.IEnergyStorage

interface IEnergyStorageModifiable : IEnergyStorage {
    fun setEnergyStored(amount: Int)
}