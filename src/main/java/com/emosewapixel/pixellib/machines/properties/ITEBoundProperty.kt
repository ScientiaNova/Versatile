package com.emosewapixel.pixellib.machines.properties

import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.google.common.reflect.MutableTypeToInstanceMap
import com.google.common.reflect.TypeToInstanceMap
import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.common.util.INBTSerializable

interface ITEBoundProperty : IMachineProperty, INBTSerializable<CompoundNBT> {
    val id: String
    val te: BaseTileEntity
    fun update() {}
    fun tick() {}
    fun detectAndSendChanges(container: BaseContainer)
    fun addCapability(map: MutableTypeToInstanceMap<ICapabilityProvider>) {}
}