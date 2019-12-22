package com.scientianovateam.versatile.machines.packets.reopening

import com.scientianovateam.versatile.machines.gui.BaseContainerProvider
import net.minecraft.network.PacketBuffer
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.network.NetworkEvent
import net.minecraftforge.fml.network.NetworkHooks
import java.util.function.Supplier

class OpenGUIPacket(val pos: BlockPos) {
    fun encode(buffer: PacketBuffer) {
        buffer.writeBlockPos(pos)
    }

    companion object {
        @JvmStatic
        fun decode(buffer: PacketBuffer) = OpenGUIPacket(buffer.readBlockPos())

        @JvmStatic
        fun processPacket(packet: OpenGUIPacket, context: Supplier<NetworkEvent.Context>) {
            context.get().enqueueWork {
                val player = context.get().sender
                NetworkHooks.openGui(player!!, BaseContainerProvider(packet.pos), packet.pos)
            }
            context.get().packetHandled = true
        }
    }
}