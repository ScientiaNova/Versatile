package com.emosewapixel.pixellib.machines.properties

import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.common.util.INBTSerializable

interface ITEBoundProperty : IMachineProperty, INBTSerializable<CompoundNBT> {
    val id: String
    val te: BaseTileEntity
    fun detectAndSendChanges(container: BaseContainer)
}