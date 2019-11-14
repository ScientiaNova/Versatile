package com.emosewapixel.pixellib.machines.packets

import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.properties.IVariableProperty
import net.minecraft.client.Minecraft
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

class UpdateIntPacket(val property: String, val value: Int) {
    fun encode(buffer: PacketBuffer) {
        buffer.writeString(property)
        buffer.writeVarInt(value)
    }

    companion object {
        fun decode(buffer: PacketBuffer) = UpdateIntPacket(buffer.readString(), buffer.readVarInt())

        @Suppress("UNCHECKED_CAST")
        fun processPacket(packet: UpdateIntPacket, context: Supplier<NetworkEvent.Context>) {
            context.get().enqueueWork {
                val container = DistExecutor.runForDist(
                        { Supplier { Minecraft.getInstance().player.openContainer as? BaseContainer } },
                        { Supplier { context.get().sender?.openContainer as? BaseContainer } }
                )
                (container?.te?.properties?.get(packet.property) as? IVariableProperty<Int>)?.setValue(packet.value, false)
                (container?.clientProperties?.get(packet.property) as? IVariableProperty<Int>)?.setValue(packet.value, false)
            }
        }
    }
}