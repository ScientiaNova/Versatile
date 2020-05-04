package com.scientianova.versatile.machines.packets

import net.minecraft.item.ItemStack
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

class UpdateHeldStackPacket(val stack: ItemStack) {
    fun encode(buffer: PacketBuffer) {
        buffer.writeItemStack(stack)
    }

    companion object {
        @JvmStatic
        fun decode(buffer: PacketBuffer) = UpdateHeldStackPacket(buffer.readItemStack())

        @JvmStatic
        fun processPacket(packet: UpdateHeldStackPacket, context: Supplier<NetworkEvent.Context>) {
            context.get().enqueueWork {
                context.get().sender?.inventory?.itemStack = packet.stack
            }
            context.get().packetHandled = true
        }
    }
}