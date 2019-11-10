package com.emosewapixel.pixellib.machines.packets

import com.emosewapixel.pixellib.extensions.readFluidStack
import com.emosewapixel.pixellib.extensions.writeFluidStack
import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.capabilities.IMutableFluidTank
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import net.minecraft.client.Minecraft
import net.minecraft.network.PacketBuffer
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

class UpdateFluidTankPacket(val pos: BlockPos, val property: String, val stack: FluidStack) {
    fun encode(buffer: PacketBuffer) {
        buffer.writeBlockPos(pos)
        buffer.writeString(property)
        buffer.writeFluidStack(stack)
    }

    companion object {
        fun decode(buffer: PacketBuffer) = UpdateFluidTankPacket(buffer.readBlockPos(), buffer.readString(), buffer.readFluidStack())

        fun processPacket(packet: UpdateFluidTankPacket, context: Supplier<NetworkEvent.Context>) {
            context.get().enqueueWork {
                val te = DistExecutor.runForDist(
                        { Supplier { Minecraft.getInstance().world.getTileEntity(packet.pos) as? BaseTileEntity } },
                        { Supplier { context.get().sender?.world?.getTileEntity(packet.pos) as? BaseTileEntity } }
                )
                (te?.properties?.get(packet.property) as? IMutableFluidTank)?.fluid = packet.stack
                ((context.get().sender?.openContainer as? BaseContainer)?.clientProperties?.get(packet.property) as? IMutableFluidTank)?.fluid = packet.stack
            }
        }
    }
}