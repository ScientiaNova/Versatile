package com.emosewapixel.pixellib.machines.properties

import com.emosewapixel.pixellib.machines.gui.BaseContainer
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.common.util.INBTSerializable
import net.minecraftforge.common.util.LazyOptional

interface IMachineProperty : INBTSerializable<CompoundNBT>, ICapabilityProvider {
    fun detectAndSendChanges(container: BaseContainer)
    fun copy(): IMachineProperty

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> = LazyOptional.empty()
}