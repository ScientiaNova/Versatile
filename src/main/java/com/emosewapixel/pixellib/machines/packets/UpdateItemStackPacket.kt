package com.emosewapixel.pixellib.machines.packets

import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import net.minecraft.client.Minecraft
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketBuffer
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.network.NetworkEvent
import net.minecraftforge.items.IItemHandlerModifiable
import java.util.function.Supplier

class UpdateItemStackPacket(val pos: BlockPos, val property: String, val slot: Int, val stack: ItemStack) {
    fun encode(buffer: PacketBuffer) {
        buffer.writeBlockPos(pos)
        buffer.writeString(property)
        buffer.writeInt(slot)
        buffer.writeItemStack(stack)
    }

    companion object {
        fun decode(buffer: PacketBuffer) = UpdateItemStackPacket(buffer.readBlockPos(), buffer.readString(), buffer.readInt(), buffer.readItemStack())

        fun processPacket(packet: UpdateItemStackPacket, context: Supplier<NetworkEvent.Context>) {
            context.get().enqueueWork {
                val te = DistExecutor.runForDist(
                        { Supplier { Minecraft.getInstance().world.getTileEntity(packet.pos) as? BaseTileEntity } },
                        { Supplier { context.get().sender?.world?.getTileEntity(packet.pos) as? BaseTileEntity } }
                )
                (te?.properties?.get(packet.property) as? IItemHandlerModifiable)?.setStackInSlot(packet.slot, packet.stack)
                ((context.get().sender?.openContainer as? BaseContainer)?.clientProperties?.get(packet.property) as? IItemHandlerModifiable)?.setStackInSlot(packet.slot, packet.stack)
            }
        }
    }
}