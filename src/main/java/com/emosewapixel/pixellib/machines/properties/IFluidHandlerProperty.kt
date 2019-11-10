package com.emosewapixel.pixellib.machines.properties

import com.emosewapixel.pixellib.machines.capabilities.IFluidHandlerModifiable

interface IFluidHandlerProperty : IMachineProperty {
    val handler: IFluidHandlerModifiable
}