package com.scientianova.versatile.machines.packets

import com.scientianova.versatile.machines.gui.BaseContainer
import com.scientianova.versatile.machines.properties.IVariableProperty
import net.minecraft.client.Minecraft
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

class UpdateStringPacket(val property: String, val value: String) {
    fun encode(buffer: PacketBuffer) {
        buffer.writeString(property)
        buffer.writeString(value)
    }

    companion object {
        @JvmStatic
        fun decode(buffer: PacketBuffer) = UpdateStringPacket(buffer.readString(), buffer.readString())

        @JvmStatic
        @Suppress("UNCHECKED_CAST")
        fun processPacket(packet: UpdateStringPacket, context: Supplier<NetworkEvent.Context>) {
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
                (container?.te?.teProperties?.get(packet.property) as? IVariableProperty<String>)
                        ?.setValue(packet.value, false)
                (container?.clientProperties?.get(packet.property) as? IVariableProperty<String>)
                        ?.setValue(packet.value, false)
            }
            context.get().packetHandled = true
        }
    }
}