package com.scientianova.versatile.machines.packets.reopening

import com.scientianova.versatile.machines.packets.NetworkHandler
import net.minecraft.client.Minecraft
import net.minecraft.inventory.container.ContainerType
import net.minecraft.network.PacketBuffer
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.network.NetworkEvent
import net.minecraftforge.registries.ForgeRegistries
import java.util.function.Supplier

class ReopenGUIPacket(val pos: BlockPos, val containerType: ContainerType<*>) {
    fun encode(buffer: PacketBuffer) {
        buffer.writeBlockPos(pos)
        buffer.writeResourceLocation(containerType.registryName!!)
    }

    companion object {
        @JvmStatic
        fun decode(buffer: PacketBuffer) = ReopenGUIPacket(buffer.readBlockPos(), ForgeRegistries.CONTAINERS.getValue(buffer.readResourceLocation())!!)

        @JvmStatic
        fun processPacket(packet: ReopenGUIPacket, context: Supplier<NetworkEvent.Context>) {
            context.get().enqueueWork {
                Minecraft.getInstance().currentScreen?.onClose()
                NetworkHandler.CHANNEL.sendToServer(OpenGUIPacket(packet.pos))
            }
            context.get().packetHandled = true
        }
    }
}