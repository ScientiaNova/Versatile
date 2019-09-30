package com.emosewapixel.pixellib.machines.packets

import com.emosewapixel.pixellib.machines.BaseTileEntity
import net.minecraft.client.Minecraft
import net.minecraft.network.PacketBuffer
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

class UpdateIntegerPacket(val pos: BlockPos, val property: String, val value: Int) {
    fun encode(buffer: PacketBuffer) {
        buffer.writeBlockPos(pos)
        buffer.writeString(property)
        buffer.writeVarInt(value)
    }

    companion object {
        fun decode(buffer: PacketBuffer) = UpdateIntegerPacket(buffer.readBlockPos(), buffer.readString(), buffer.readVarInt())

        fun processPacket(packet: UpdateIntegerPacket, context: Supplier<NetworkEvent.Context>) {
            context.get().enqueueWork {
                val te = DistExecutor.runForDist(
                        { Supplier { Minecraft.getInstance().world.getTileEntity(packet.pos) as? BaseTileEntity } },
                        { Supplier { context.get().sender?.world?.getTileEntity(packet.pos) as? BaseTileEntity } }
                )
                te?.properties?.set(packet.property, packet.value)
            }
        }
    }
}