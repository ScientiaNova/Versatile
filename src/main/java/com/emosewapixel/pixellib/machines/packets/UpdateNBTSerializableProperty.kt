package com.emosewapixel.pixellib.machines.packets

import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.properties.IValueProperty
import net.minecraft.client.Minecraft
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.PacketBuffer
import net.minecraft.util.math.BlockPos
import net.minecraftforge.common.util.INBTSerializable
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

class UpdateNBTSerializableProperty(val pos: BlockPos, val property: String, val nbt: CompoundNBT) {
    fun encode(buffer: PacketBuffer) {
        buffer.writeBlockPos(pos)
        buffer.writeString(property)
        buffer.writeCompoundTag(nbt)
    }

    companion object {
        fun decode(buffer: PacketBuffer) = UpdateNBTSerializableProperty(buffer.readBlockPos(), buffer.readString(), buffer.readCompoundTag()!!)

        fun processPacket(packet: UpdateNBTSerializableProperty, context: Supplier<NetworkEvent.Context>) {
            context.get().enqueueWork {
                val te = DistExecutor.runForDist(
                        { Supplier { Minecraft.getInstance().world.getTileEntity(packet.pos) as? BaseTileEntity } },
                        { Supplier { context.get().sender?.world?.getTileEntity(packet.pos) as? BaseTileEntity } }
                )
                ((te?.properties?.get(packet.property) as? IValueProperty<*>)?.value as? INBTSerializable<CompoundNBT>)?.deserializeNBT(packet.nbt)
            }
        }
    }
}