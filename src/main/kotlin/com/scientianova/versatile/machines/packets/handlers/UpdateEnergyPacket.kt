package com.scientianova.versatile.machines.packets.handlers

import com.scientianovateam.versatile.machines.capabilities.energy.IEnergyStorageModifiable
import com.scientianovateam.versatile.machines.gui.BaseContainer
import com.scientianovateam.versatile.machines.properties.IValueProperty
import net.minecraft.client.Minecraft
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

class UpdateEnergyPacket(val property: String, val value: Int) {
    fun encode(buffer: PacketBuffer) {
        buffer.writeString(property)
        buffer.writeVarInt(value)
    }

    companion object {
        @JvmStatic
        fun decode(buffer: PacketBuffer) = UpdateEnergyPacket(buffer.readString(), buffer.readVarInt())

        @JvmStatic
        @Suppress("UNCHECKED_CAST")
        fun processPacket(packet: UpdateEnergyPacket, context: Supplier<NetworkEvent.Context>) {
            context.get().enqueueWork {
                val serverSideContainer = context.get().sender?.openContainer as? BaseContainer
                val container = DistExecutor.runForDist(
                        {
                            Supplier {
                                serverSideContainer ?: Minecraft.getInstance().player.openContainer as? BaseContainer
                            }
                        },
                        { Supplier { serverSideContainer } }
                )
                (container?.te?.teProperties?.get(packet.property) as? IValueProperty<IEnergyStorageModifiable>)?.value?.energyStored = packet.value
                (container?.clientProperties?.get(packet.property) as? IValueProperty<IEnergyStorageModifiable>)?.value?.energyStored = packet.value
            }
            context.get().packetHandled = true
        }
    }
}