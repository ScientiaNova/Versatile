package com.scientianova.versatile.common.extensions

import net.minecraft.nbt.ByteArrayNBT
import net.minecraft.nbt.CompoundNBT
import net.minecraft.nbt.INBT
import net.minecraft.nbt.ListNBT
import net.minecraftforge.common.util.INBTSerializable
import net.minecraftforge.fluids.FluidStack
import java.util.*

class NBTBuilder(builder: NBTBuilder.() -> Unit) {
    val result = CompoundNBT()

    init {
        builder()
    }

    infix fun String.to(property: Boolean) = result.putBoolean(this, property)
    infix fun String.to(property: Double) = result.putDouble(this, property)
    infix fun String.to(property: Float) = result.putFloat(this, property)
    infix fun String.to(property: Short) = result.putShort(this, property)
    infix fun String.to(property: String) = result.putString(this, property)
    infix fun String.to(property: UUID) = result.putUniqueId(this, property)

    infix fun String.to(property: Byte) = result.putByte(this, property)
    infix fun String.to(property: ByteArray) = result.putByteArray(this, property)
    @JvmName("toByteArray")
    infix fun String.to(property: MutableList<Byte>) = result.put(this, ByteArrayNBT(property))

    infix fun String.to(property: Int) = result.putInt(this, property)
    infix fun String.to(property: IntArray) = result.putIntArray(this, property)
    @JvmName("toIntArray")
    infix fun String.to(property: MutableList<Int>) = result.putIntArray(this, property)

    infix fun String.to(property: Long) = result.putLong(this, property)
    infix fun String.to(property: LongArray) = result.putLongArray(this, property)
    @JvmName("toLongArray")
    infix fun String.to(property: MutableList<Long>) = result.putLongArray(this, property)

    infix fun String.to(property: FluidStack) = result.put(this, property.writeToNBT(CompoundNBT()))

    infix fun String.to(property: INBT) = result.put(this, property)
    infix fun String.to(property: INBTSerializable<*>) = result.put(this, property.serializeNBT())
    infix fun String.to(property: Collection<INBT>) = result.put(this, property.toListNBT())
    operator fun String.invoke(builder: NBTBuilder.() -> Unit) = result.put(this, NBTBuilder(builder).result)
}

fun nbt(builder: NBTBuilder.() -> Unit) = NBTBuilder(builder).result

operator fun CompoundNBT.plusAssign(other: CompoundNBT) {
    merge(other)
}

@JvmName("plusInt")
operator fun CompoundNBT.plusAssign(pair: Pair<String, Int>) = putInt(pair.first, pair.second)

@JvmName("plusFloat")
operator fun CompoundNBT.plusAssign(pair: Pair<String, Float>) = putFloat(pair.first, pair.second)

@JvmName("plusDouble")
operator fun CompoundNBT.plusAssign(pair: Pair<String, Double>) = putDouble(pair.first, pair.second)

@JvmName("plusByte")
operator fun CompoundNBT.plusAssign(pair: Pair<String, Byte>) = putByte(pair.first, pair.second)

@JvmName("plusShort")
operator fun CompoundNBT.plusAssign(pair: Pair<String, Short>) = putShort(pair.first, pair.second)

@JvmName("plusBoolean")
operator fun CompoundNBT.plusAssign(pair: Pair<String, Boolean>) = putBoolean(pair.first, pair.second)

@JvmName("plusLong")
operator fun CompoundNBT.plusAssign(pair: Pair<String, Long>) = putLong(pair.first, pair.second)

@JvmName("plusString")
operator fun CompoundNBT.plusAssign(pair: Pair<String, String>) = putString(pair.first, pair.second)

@JvmName("plusNBT")
operator fun CompoundNBT.plusAssign(pair: Pair<String, INBT>) {
    put(pair.first, pair.second)
}

@JvmName("plusSerializable")
operator fun CompoundNBT.plusAssign(pair: Pair<String, INBTSerializable<*>>) {
    put(pair.first, pair.second.serializeNBT())
}

fun Collection<INBT>.toListNBT(): ListNBT {
    val list = ListNBT()
    this.forEach { list.add(it) }
    return list
}