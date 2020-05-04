package com.scientianova.versatile.machines.packets.primitives

import com.scientianova.versatile.machines.gui.BaseContainer
import com.scientianova.versatile.machines.properties.IVariableProperty
import net.minecraft.client.Minecraft
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

class UpdateBooleanPacket(val property: String, val value: Boolean) {
    fun encode(buffer: PacketBuffer) {
        buffer.writeString(property)
        buffer.writeBoolean(value)
    }

    companion object {
        @JvmStatic
        fun decode(buffer: PacketBuffer) = UpdateBooleanPacket(buffer.readString(), buffer.readBoolean())

        @JvmStatic
        @Suppress("UNCHECKED_CAST")
        fun processPacket(packet: UpdateBooleanPacket, context: Supplier<NetworkEvent.Context>) {
            context.get().enqueueWork {
                val serverSideContainer = context.get().sender?.openContainer as? BaseContainer
                val container = DistExecutor.runForDist(
                        {
                            Supplier {
                                serverSideContainer ?: Minecraft.getInstance().player!!.openContainer as? BaseContainer
                            }
                        },
                        { Supplier { serverSideContainer } }
                )
                (container?.te?.teProperties?.get(packet.property) as? IVariableProperty<Boolean>)
                        ?.setValue(packet.value, false)
                (container?.clientProperties?.get(packet.property) as? IVariableProperty<Boolean>)
                        ?.setValue(packet.value, false)
            }
            context.get().packetHandled = true
        }
    }
}