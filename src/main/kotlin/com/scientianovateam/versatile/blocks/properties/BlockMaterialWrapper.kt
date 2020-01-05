package com.scientianovateam.versatile.blocks.properties

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.*
import com.scientianovateam.versatile.common.serialization.IJSONSerializer
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.block.material.PushReaction
import net.minecraft.util.ResourceLocation

data class BlockMaterialWrapper(
        val registryName: ResourceLocation,
        val color: MaterialColor,
        val liquid: Boolean = false,
        val solid: Boolean = true,
        val blocksMovement: Boolean = true,
        val opaque: Boolean = true,
        val requiresNoTool: Boolean = false,
        val flammable: Boolean = false,
        val replaceable: Boolean = false,
        val pushReaction: PushReaction = PushReaction.NORMAL
) {
    val material = Material(color, liquid, solid, blocksMovement, opaque, requiresNoTool, flammable, replaceable, pushReaction)

    object Serializer : IJSONSerializer<BlockMaterialWrapper, JsonObject> {
        override fun read(json: JsonObject) = BlockMaterialWrapper(
                registryName = json.getStringOrNull("name")?.toResLoc() ?: error("Missing registry name for block material"),
                color = json.getIntOrNull("color")?.let { MaterialColor.COLORS[it] } ?: error("Missing material color for block material"),
                liquid = json.getBooleanOrNull("liquid") ?: false,
                solid = json.getBooleanOrNull("solid") ?: true,
                blocksMovement = json.getBooleanOrNull("blocks_movement") ?: true,
                opaque = json.getBooleanOrNull("opaque") ?: true,
                requiresNoTool = json.getBooleanOrNull("requires_no_tool") ?: false,
                flammable = json.getBooleanOrNull("flammable") ?: false,
                replaceable = json.getBooleanOrNull("replaceable") ?: false,
                pushReaction = json.getStringOrNull("push_reaction")?.let { PushReaction.valueOf(it.toUpperCase()) } ?: PushReaction.NORMAL
        )

        override fun write(obj: BlockMaterialWrapper) = json {
            "color" to obj.color.colorIndex
            "is_liquid" to obj.liquid
            "is_solid" to obj.solid
            "blocks_movement" to obj.blocksMovement
            "is_opaque" to obj.opaque
            "requires_no_tool" to obj.requiresNoTool
            "flammable" to obj.flammable
            "replaceable" to obj.replaceable
            "push_reaction" to obj.pushReaction.name.toLowerCase()
        }
    }
}