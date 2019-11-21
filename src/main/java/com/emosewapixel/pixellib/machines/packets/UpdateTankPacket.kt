package com.emosewapixel.pixellib.machines.packets

import com.emosewapixel.pixellib.extensions.readFluidStack
import com.emosewapixel.pixellib.extensions.writeFluidStack
import com.emosewapixel.pixellib.machines.capabilities.IFluidHandlerModifiable
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.properties.IValueProperty
import net.minecraft.client.Minecraft
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

class UpdateTankPacket(val property: String, val index: Int, val stack: FluidStack) {
    fun encode(buffer: PacketBuffer) {
        buffer.writeString(property)
        buffer.writeVarInt(index)
        buffer.writeFluidStack(stack)
    }

    companion object {
        @JvmStatic
        fun decode(buffer: PacketBuffer) = UpdateTankPacket(buffer.readString(), buffer.readVarInt(), buffer.readFluidStack())

        @JvmStatic
        @Suppress("UNCHECKED_CAST")
        fun processPacket(packet: UpdateTankPacket, context: Supplier<NetworkEvent.Context>) {
            context.get().enqueueWork {
                val serverSideContainer = context.get().sender?.openContainer as? BaseContainer
                val container = DistExecutor.runForDist(
                        {
                            Supplier {
                                serverSideContainer ?: Minecraft.getInstance().player.openContainer as? BaseContainer
                            }
                        },
                        { Supplier { serverSideContainer } }
                )
                (container?.te?.teProperties?.get(packet.property) as? IValueProperty<IFluidHandlerModifiable>)
                        ?.value?.setFluidInTank(packet.index, packet.stack)
                (container?.clientProperties?.get(packet.property) as? IValueProperty<IFluidHandlerModifiable>)
                        ?.value?.setFluidInTank(packet.index, packet.stack.copy())
            }
            context.get().packetHandled = true
        }
    }
}