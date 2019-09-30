package com.emosewapixel.pixellib.machines.packets

import com.emosewapixel.pixellib.extensions.readFluidStack
import com.emosewapixel.pixellib.extensions.writeFluidStack
import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.capabilities.IMutableFluidHandler
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import net.minecraft.client.Minecraft
import net.minecraft.network.PacketBuffer
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

class UpdateFluidStackPacket(val pos: BlockPos, val property: String, val slot: Int, val stack: FluidStack) {
    fun encode(buffer: PacketBuffer) {
        buffer.writeBlockPos(pos)
        buffer.writeString(property)
        buffer.writeInt(slot)
        buffer.writeFluidStack(stack)
    }

    companion object {
        fun decode(buffer: PacketBuffer) = UpdateFluidStackPacket(buffer.readBlockPos(), buffer.readString(), buffer.readInt(), buffer.readFluidStack())

        fun processPacket(packet: UpdateFluidStackPacket, context: Supplier<NetworkEvent.Context>) {
            context.get().enqueueWork {
                val te = DistExecutor.runForDist(
                        { Supplier { Minecraft.getInstance().world.getTileEntity(packet.pos) as? BaseTileEntity } },
                        { Supplier { context.get().sender?.world?.getTileEntity(packet.pos) as? BaseTileEntity } }
                )
                (te?.properties?.get(packet.property) as? IMutableFluidHandler)?.setFluidInTank(packet.slot, packet.stack)
                ((context.get().sender?.openContainer as? BaseContainer)?.clientProperties?.get(packet.property) as? IMutableFluidHandler)?.setFluidInTank(packet.slot, packet.stack)
            }
        }
    }
}