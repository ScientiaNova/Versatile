package com.scientianovateam.versatile.blocks.serializable

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.serialization.IJSONSerializer
import net.minecraft.block.Block
import net.minecraft.util.text.ITextComponent
import net.minecraftforge.common.extensions.IForgeBlock
import net.minecraftforge.registries.IForgeRegistryEntry

interface ISerializableBlock : IForgeBlock, IForgeRegistryEntry<Block> {
    fun setLocalization(function: () -> ITextComponent)
    val serializer: IJSONSerializer<out ISerializableBlock, JsonObject>
    val renderType: String
}

@Suppress("UNCHECKED_CAST")
fun <T : ISerializableBlock> T.serialize() = (serializer as IJSONSerializer<T, JsonObject>).write(this)