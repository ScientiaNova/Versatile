package com.emosewapixel.pixellib.extensions

import io.netty.buffer.ByteBuf
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.PacketBuffer
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.util.text.ITextComponent
import net.minecraftforge.common.util.INBTSerializable
import java.nio.ByteBuffer
import java.util.*

infix fun PacketBuffer.write(value: Boolean) = writeBoolean(value)
infix fun PacketBuffer.write(value: Byte) = writeByte(value.toInt())
infix fun PacketBuffer.write(value: ByteBuf) = writeBytes(value)
infix fun PacketBuffer.write(value: ByteBuffer) = writeBytes(value)
infix fun PacketBuffer.write(value: ByteArray) = writeByteArray(value)
infix fun PacketBuffer.write(value: Short) = writeShort(value.toInt())
infix fun PacketBuffer.write(value: Int) = writeInt(value)
infix fun PacketBuffer.write(value: Float) = writeFloat(value)
infix fun PacketBuffer.write(value: Double) = writeDouble(value)
infix fun PacketBuffer.write(value: Long) = writeLong(value)
infix fun PacketBuffer.write(value: BlockPos) = writeBlockPos(value)
infix fun PacketBuffer.write(value: BlockRayTraceResult) = writeBlockRay(value)
infix fun PacketBuffer.write(value: Char) = writeChar(value.toInt())
infix fun PacketBuffer.write(value: String) = writeString(value)
infix fun PacketBuffer.write(value: ResourceLocation) = writeResourceLocation(value)
infix fun PacketBuffer.write(value: ITextComponent) = writeTextComponent(value)
infix fun PacketBuffer.write(value: CompoundNBT) = writeCompoundTag(value)
infix fun PacketBuffer.write(value: INBTSerializable<CompoundNBT>) = writeCompoundTag(value.serializeNBT())
infix fun PacketBuffer.write(value: ItemStack) = writeItemStack(value)
infix fun PacketBuffer.write(value: Enum<*>) = writeEnumValue(value)
infix fun PacketBuffer.write(value: Date) = writeTime(value)
infix fun PacketBuffer.write(value: UUID) = writeUniqueId(value)

infix fun PacketBuffer.writeVar(value: Int) = writeVarInt(value)
infix fun PacketBuffer.writeVar(value: IntArray) = writeVarIntArray(value)
infix fun PacketBuffer.writeVar(value: Long) = writeVarLong(value)