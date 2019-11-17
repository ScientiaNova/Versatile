package com.emosewapixel.pixellib.machines.packets

import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.properties.IValueProperty
import net.minecraft.client.Minecraft
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.PacketBuffer
import net.minecraftforge.common.util.INBTSerializable
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

class UpdateNBTSerializableProperty(val property: String, val nbt: CompoundNBT) {
    fun encode(buffer: PacketBuffer) {
        buffer.writeString(property)
        buffer.writeCompoundTag(nbt)
    }

    companion object {
        fun decode(buffer: PacketBuffer) = UpdateNBTSerializableProperty(buffer.readString(), buffer.readCompoundTag()!!)

        @Suppress("UNCHECKED_CAST")
        fun processPacket(packet: UpdateNBTSerializableProperty, context: Supplier<NetworkEvent.Context>) {
            context.get().enqueueWork {
                val container = DistExecutor.runForDist(
                        { Supplier { Minecraft.getInstance().player.openContainer as? BaseContainer } },
                        { Supplier { context.get().sender?.openContainer as? BaseContainer } }
                )
                ((container?.te?.teProperties?.get(packet.property) as? IValueProperty<*>)?.value as? INBTSerializable<CompoundNBT>)?.deserializeNBT(packet.nbt)
                ((container?.clientProperties?.get(packet.property) as? IValueProperty<*>)?.value as? INBTSerializable<CompoundNBT>)?.deserializeNBT(packet.nbt)
            }
            context.get().packetHandled = true
        }
    }
}