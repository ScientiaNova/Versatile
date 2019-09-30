package com.emosewapixel.pixellib.extensions

import net.minecraft.network.PacketBuffer
import net.minecraft.util.registry.Registry
import net.minecraftforge.fluids.FluidStack

fun PacketBuffer.writeFluidStack(stack: FluidStack): PacketBuffer {
    if (stack.isEmpty)
        writeBoolean(false)
    else {
        writeBoolean(true)
        writeVarInt(Registry.FLUID.getId(stack.fluid))
        writeByte(stack.amount)
        writeCompoundTag(stack.tag)
    }

    return this
}

fun PacketBuffer.readFluidStack(): FluidStack = if (!readBoolean()) FluidStack.EMPTY else {
    val i = readVarInt()
    val j = readByte().toInt()
    val fluidStack = FluidStack(Registry.FLUID.getByValue(i), j)
    fluidStack.tag = readCompoundTag()
    fluidStack
}