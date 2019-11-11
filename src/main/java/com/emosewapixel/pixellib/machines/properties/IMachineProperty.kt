package com.emosewapixel.pixellib.machines.properties

import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.common.util.LazyOptional

interface IMachineProperty : ICapabilityProvider {
    fun copy(): IMachineProperty

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> = LazyOptional.empty()
}