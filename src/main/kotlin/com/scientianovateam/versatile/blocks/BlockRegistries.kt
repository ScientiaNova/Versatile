package com.scientianovateam.versatile.blocks

import com.google.gson.JsonObject
import com.scientianovateam.versatile.blocks.properties.BlockMaterialWrapper
import com.scientianovateam.versatile.blocks.properties.SoundTypeV
import com.scientianovateam.versatile.blocks.serializable.ISerializableBlock
import com.scientianovateam.versatile.common.registry.Registry
import com.scientianovateam.versatile.common.serialization.IRegisterableJSONSerializer

val BLOCK_SERIALIZERS = Registry<IRegisterableJSONSerializer<out ISerializableBlock, JsonObject>>()
val SERIALIZED_BLOCKS = Registry<ISerializableBlock>()
val BLOCK_MATERIALS = Registry<BlockMaterialWrapper>()
val SOUND_TYPES = Registry<SoundTypeV>()