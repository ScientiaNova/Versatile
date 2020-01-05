package com.scientianovateam.versatile.blocks

import com.google.gson.JsonObject
import com.scientianovateam.versatile.blocks.properties.BlockMaterialWrapper
import com.scientianovateam.versatile.blocks.properties.SoundTypeV
import com.scientianovateam.versatile.common.registry.Registry
import com.scientianovateam.versatile.common.serialization.IJSONSerializer
import com.scientianovateam.versatile.common.serialization.IRegisterableJSONSerializer
import net.minecraft.block.Block

val BLOCK_SERIALIZERS = Registry<IRegisterableJSONSerializer<out Block, JsonObject>>()
val SERIALIZED_BLOCKS = Registry<Block>()
val BLOCK_MATERIALS = Registry<BlockMaterialWrapper>()
val SOUND_TYPES = Registry<SoundTypeV>()