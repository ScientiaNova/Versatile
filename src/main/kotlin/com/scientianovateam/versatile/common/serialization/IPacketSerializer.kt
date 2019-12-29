package com.scientianovateam.versatile.common.serialization

import net.minecraft.network.PacketBuffer

interface IPacketSerializer<T> {
    fun read(packet: PacketBuffer): T
    fun write(packet: PacketBuffer, obj: T)
}