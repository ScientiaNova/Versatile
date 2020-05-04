package com.scientianova.versatile.machines.capabilities.energy

import com.scientianovateam.versatile.common.extensions.nbt
import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.common.util.INBTSerializable

open class EnergyHandler(private val max: Int) : IEnergyStorageModifiable, INBTSerializable<CompoundNBT> {
    override fun getMaxEnergyStored() = max

    private var stored = 0

    override fun getEnergyStored() = stored

    override fun setEnergyStored(amount: Int) {
        stored = amount
        onUpdate()
    }

    override fun canExtract() = true

    override fun extractEnergy(maxExtract: Int, simulate: Boolean): Int {
        val extracted = maxExtract.coerceAtMost(stored)
        if (!simulate) {
            stored -= extracted
            onUpdate()
        }
        return extracted
    }

    override fun canReceive() = true

    override fun receiveEnergy(maxReceive: Int, simulate: Boolean): Int {
        val received = maxReceive.coerceAtMost(max - stored)
        if (!simulate) {
            stored += received
            onUpdate()
        }
        return received
    }

    override fun serializeNBT() = nbt {
        "energy" to stored
    }

    override fun deserializeNBT(nbt: CompoundNBT?) {
        stored = nbt?.getInt("energy") ?: 0
        onLoad()
    }

    open fun onLoad() {}

    open fun onUpdate() {}
}