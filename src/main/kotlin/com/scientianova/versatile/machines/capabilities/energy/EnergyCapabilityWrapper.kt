package com.scientianova.versatile.machines.capabilities.energy

import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.energy.IEnergyStorage

open class EnergyCapabilityWrapper : IEnergyStorage, ICapabilityProvider {
    protected val handlers = arrayListOf<IEnergyStorage>()

    fun addHandler(handler: IEnergyStorage) = handlers.add(handler)

    override fun canExtract() = handlers.any(IEnergyStorage::canExtract)

    override fun canReceive() = handlers.any(IEnergyStorage::canReceive)

    override fun getEnergyStored() = handlers.map(IEnergyStorage::getEnergyStored).sum()

    override fun getMaxEnergyStored() = handlers.map(IEnergyStorage::getMaxEnergyStored).sum()

    override fun extractEnergy(maxExtract: Int, simulate: Boolean) = maxExtract - handlers.filter(IEnergyStorage::canExtract)
            .fold(maxExtract) { acc, handler -> acc - handler.extractEnergy(acc, simulate) }

    override fun receiveEnergy(maxReceive: Int, simulate: Boolean) = maxReceive - handlers.filter(IEnergyStorage::canReceive)
            .fold(maxReceive) { acc, handler -> acc - handler.receiveEnergy(acc, simulate) }

    val optional = LazyOptional.of { this }

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> = if (cap === CapabilityEnergy.ENERGY) optional.cast() else LazyOptional.empty()
}