package com.emosewapixel.pixellib.machines.packets

import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.recipes.SimpleMachineRecipe
import net.minecraft.client.Minecraft
import net.minecraft.network.PacketBuffer
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

class UpdateRecipeTimePacket(val pos: BlockPos, val property: String, val value: Int) {
    fun encode(buffer: PacketBuffer) {
        buffer.writeBlockPos(pos)
        buffer.writeString(property)
        buffer.writeVarInt(value)
    }

    companion object {
        fun decode(buffer: PacketBuffer) = UpdateRecipeTimePacket(buffer.readBlockPos(), buffer.readString(), buffer.readVarInt())

        fun processPacket(packet: UpdateRecipeTimePacket, context: Supplier<NetworkEvent.Context>) {
            context.get().enqueueWork {
                val te = DistExecutor.runForDist(
                        { Supplier { Minecraft.getInstance().world.getTileEntity(packet.pos) as? BaseTileEntity } },
                        { Supplier { context.get().sender?.world?.getTileEntity(packet.pos) as? BaseTileEntity } }
                )
                (te?.properties?.get(packet.property) as? SimpleMachineRecipe)?.time = packet.value
            }
        }
    }
}