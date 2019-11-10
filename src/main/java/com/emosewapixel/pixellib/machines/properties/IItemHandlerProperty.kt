package com.emosewapixel.pixellib.machines.properties

import net.minecraftforge.items.IItemHandlerModifiable

interface IItemHandlerProperty : IMachineProperty {
    val handler: IItemHandlerModifiable
}