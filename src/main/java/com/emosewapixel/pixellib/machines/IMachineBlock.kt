package com.emosewapixel.pixellib.machines

import com.emosewapixel.pixellib.machines.gui.layout.GUIBook
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty

interface IMachineBlock {
    val properties: BaseTileEntity.() -> List<ITEBoundProperty>

    val tick: BaseTileEntity.() -> Unit

    val update: BaseTileEntity.() -> Unit

    val guiLayout: BaseTileEntity.() -> GUIBook
}