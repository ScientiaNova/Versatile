package com.scientianovateam.versatile.machines.packets.handlers

import com.scientianovateam.versatile.machines.gui.BaseContainer
import com.scientianovateam.versatile.machines.properties.IValueProperty
import net.minecraft.client.Minecraft
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.network.NetworkEvent
import net.minecraftforge.items.IItemHandlerModifiable
import java.util.function.Supplier

class UpdateSlotPacket(val property: String, val index: Int, val stack: ItemStack) {
    fun encode(buffer: PacketBuffer) {
        buffer.writeString(property)
        buffer.writeVarInt(index)
        buffer.writeItemStack(stack)
    }

    companion object {
        @JvmStatic
        fun decode(buffer: PacketBuffer) = UpdateSlotPacket(buffer.readString(), buffer.readVarInt(), buffer.readItemStack())

        @JvmStatic
        @Suppress("UNCHECKED_CAST")
        fun processPacket(packet: UpdateSlotPacket, context: Supplier<NetworkEvent.Context>) {
            context.get().enqueueWork {
                val serverSideContainer = context.get().sender?.openContainer as? BaseContainer
                val container = DistExecutor.runForDist(
                        {
                            Supplier {
                                serverSideContainer ?: Minecraft.getInstance().player?.openContainer as? BaseContainer
                            }
                        },
                        { Supplier { serverSideContainer } }
                )
                (container?.te?.teProperties?.get(packet.property) as? IValueProperty<IItemHandlerModifiable>)
                        ?.value?.setStackInSlot(packet.index, packet.stack.copy())
                (container?.clientProperties?.get(packet.property) as? IValueProperty<IItemHandlerModifiable>)
                        ?.value?.setStackInSlot(packet.index, packet.stack.copy())
            }
            context.get().packetHandled = true
        }
    }
}