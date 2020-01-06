package com.scientianovateam.versatile.machines.packets.reopening

import com.scientianovateam.versatile.machines.BaseTileEntity
import net.minecraft.network.PacketBuffer
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

class ChangePagePacket(val pos: BlockPos, val pageId: Int) {
    fun encode(buffer: PacketBuffer) {
        buffer.writeBlockPos(pos)
        buffer.writeInt(pageId)
    }

    companion object {
        @JvmStatic
        fun decode(buffer: PacketBuffer) = ChangePagePacket(buffer.readBlockPos(), buffer.readInt())

        @JvmStatic
        fun processPacket(packet: ChangePagePacket, context: Supplier<NetworkEvent.Context>) {
            context.get().enqueueWork {
                (context.get().sender?.world?.getTileEntity(packet.pos) as? BaseTileEntity)?.guiLayout?.setCurrentPage(packet.pageId)
            }
            context.get().packetHandled = true
        }
    }
}