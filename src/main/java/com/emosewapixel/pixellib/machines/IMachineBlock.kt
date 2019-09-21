package com.emosewapixel.pixellib.machines

import com.emosewapixel.pixellib.machines.gui.layout.GUIBook
import net.minecraft.nbt.CompoundNBT

interface IMachineBlock {
    val properties: Map<String, () -> Any>

    val tick: BaseTileEntity.() -> Unit

    val update: BaseTileEntity.() -> Unit

    val serializeNBT: BaseTileEntity.() -> CompoundNBT

    val deserializeNBT: BaseTileEntity.(CompoundNBT) -> Unit

    val guiLayout: () -> GUIBook
}