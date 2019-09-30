package com.emosewapixel.pixellib.machines.packets

import com.emosewapixel.pixellib.machines.BaseTileEntity
import net.minecraft.client.Minecraft
import net.minecraft.network.PacketBuffer
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

class UpdateBooleanPacket(val pos: BlockPos, val property: String, val value: Boolean) {
    fun encode(buffer: PacketBuffer) {
        buffer.writeBlockPos(pos)
        buffer.writeString(property)
        buffer.writeBoolean(value)
    }

    companion object {
        fun decode(buffer: PacketBuffer) = UpdateBooleanPacket(buffer.readBlockPos(), buffer.readString(), buffer.readBoolean())

        fun processPacket(packet: UpdateBooleanPacket, context: Supplier<NetworkEvent.Context>) {
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