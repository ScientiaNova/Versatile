package com.scientianovateam.versatile.blocks.properties

import com.google.gson.JsonObject
import com.scientianovateam.versatile.blocks.BLOCK_MATERIALS
import com.scientianovateam.versatile.blocks.SOUND_TYPES
import com.scientianovateam.versatile.common.extensions.*
import com.scientianovateam.versatile.common.serialization.IJSONSerializer
import net.minecraft.block.Block
import net.minecraft.block.material.MaterialColor
import net.minecraft.block.material.PushReaction
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.util.math.shapes.VoxelShapes
import net.minecraftforge.common.ToolType

open class ExtendedBlockProperties(
        val name: String,
        val material: BlockMaterialWrapper,
        val materialColor: MaterialColor = material.color,
        val blocksMovement: Boolean = material.blocksMovement,
        val sound: SoundTypeV = SOUND_TYPES["stone".toResLoc()]!!,
        val lightValue: Int = 0,
        val hardness: Float = 0f,
        val resistance: Float = 0f,
        val ticksRandomly: Boolean = false,
        val slipperiness: Float = .6f,
        val speedMultiplier: Float = 1f,
        val jumpMultiplier: Float = 1f,
        val harvestLevel: Int = -1,
        val harvestTool: ToolType? = null,
        val isAir: Boolean = false,
        val translationKey: String = "block.$name",
        val renderType: String = "solid",
        val opaque: Boolean = true,
        val shape: VoxelShape = VoxelShapes.fullCube(),
        val collisionShape: VoxelShape = if (blocksMovement) shape else VoxelShapes.empty(),
        val renderShape: VoxelShape = shape,
        val canDropFromExplosion: Boolean = true,
        val climbable: Boolean = false,
        val burning: Boolean = false,
        val canMobsSpawn: Boolean = true,
        val canBeReplacedByLeaves: Boolean = !isAir,
        val canBeReplacedByLogs: Boolean = !isAir,
        val foliage: Boolean = false,
        val fertile: Boolean = false,
        val xpDrop: Int = 0,
        val enchantingPowerBonus: Float = 0f,
        val sticky: Boolean = false,
        val flammability: Int = 0,
        val fireSpreadSpeed: Int = 0,
        val fireSource: Boolean = false,
        val pushReaction: PushReaction = material.pushReaction
) : Block.Properties(material.material, materialColor) {
    init {
        if (!blocksMovement) super.doesNotBlockMovement()
        super.sound(sound)
        super.lightValue(lightValue)
        super.hardnessAndResistance(hardness, resistance)
        if (!opaque) super.nonOpaque()
        if (ticksRandomly) super.tickRandomly()
        super.slipperiness(slipperiness)
        super.velocityMultiplier(speedMultiplier)
        super.jumpVelocityMultiplier(jumpMultiplier)
        super.harvestLevel(harvestLevel)
        if (harvestTool != null) super.harvestTool(harvestTool)
    }

    object Serializer : IJSONSerializer<ExtendedBlockProperties, JsonObject> {
        override fun read(json: JsonObject): ExtendedBlockProperties {
            val name = json.getStringOrNull("name") ?: error("Missing block mae")
            val material = json.getStringOrNull("material")?.let { BLOCK_MATERIALS[it.toResLoc()] }
                    ?: error("Missing block material")
            val blocksMovement = json.getBooleanOrNull("blocks_movement") ?: material.blocksMovement
            val renderType = json.getStringOrNull("render_type") ?: "solid"
            return ExtendedBlockProperties(
                    name = name,
                    material = material,
                    materialColor = json.getIntOrNull("material_color")?.let { MaterialColor.COLORS[it] }
                            ?: material.color,
                    blocksMovement = blocksMovement,
                    sound = SOUND_TYPES[(json.getStringOrNull("sound_type") ?: "stone").toResLoc()]!!,
                    lightValue = json.getIntOrNull("light_value") ?: 0,
                    hardness = json.getFloatOrNull("hardness") ?: 0f,
                    resistance = json.getFloatOrNull("resistance") ?: 0f,
                    ticksRandomly = json.getBooleanOrNull("ticks_randomly") ?: false,
                    slipperiness = json.getFloatOrNull("slipperiness") ?: .6f,
                    speedMultiplier = json.getFloatOrNull("speed_multiplier") ?: 1f,
                    jumpMultiplier = json.getFloatOrNull("jump_multiplier") ?: 1f,
                    harvestLevel = json.getIntOrNull("harvest_level") ?: -1,
                    harvestTool = json.getStringOrNull("harvest_tool")?.let(ToolType::get),
                    isAir = json.getBooleanOrNull("is_air") ?: false,
                    translationKey = json.getStringOrNull("translation_key") ?: "block.$name",
                    renderType = renderType,
                    opaque = json.getBooleanOrNull("opaque") ?: true,
                    //TODO Voxel Shape Serializer
                    canDropFromExplosion = json.getBooleanOrNull("can_drop_from_explosion") ?: true,
                    climbable = json.getBooleanOrNull("climbable") ?: false,
                    burning = json.getBooleanOrNull("burning") ?: false,
                    canMobsSpawn = json.getBooleanOrNull("can_mobs_spawn") ?: true,
                    canBeReplacedByLeaves = json.getBooleanOrNull("can_be_replaced_by_leaves") ?: false,
                    canBeReplacedByLogs = json.getBooleanOrNull("can_be_replaced_by_logs") ?: false,
                    foliage = json.getBooleanOrNull("foliage") ?: false,
                    fertile = json.getBooleanOrNull("fertile") ?: false,
                    xpDrop = json.getIntOrNull("xp_drop") ?: 0,
                    enchantingPowerBonus = json.getFloatOrNull("enchanting_power_boost") ?: 0f,
                    sticky = json.getBooleanOrNull("sticky") ?: false,
                    flammability = json.getIntOrNull("flammability") ?: 0,
                    fireSpreadSpeed = json.getIntOrNull("fire_spread_speed") ?: 0,
                    fireSource = json.getBooleanOrNull("fire_source") ?: false,
                    pushReaction = json.getStringOrNull("push_reaction")?.let { PushReaction.valueOf(it.toUpperCase()) }
                            ?: material.pushReaction
            )
        }

        override fun write(obj: ExtendedBlockProperties) = json {
            "material" to obj.material.registryName
            if (obj.materialColor != obj.material.color) "material_color" to obj.materialColor
            if (obj.blocksMovement != obj.material.blocksMovement) "blocks_movement" to obj.blocksMovement
            if (obj.sound.registryName != "stone".toResLoc()) "sound" to obj.sound.registryName
            if (obj.lightValue > 0) "light_value" to obj.lightValue
            if (obj.hardness != 0f) "hardness" to obj.hardness
            if (obj.resistance != 0f) "resistance" to obj.resistance
            if (obj.ticksRandomly) "ticks_randomly" to obj.ticksRandomly
            if (obj.slipperiness != .6f) "slipperiness" to obj.slipperiness
            if (obj.speedMultiplier != 1f) "speed_multiplier" to obj.speedMultiplier
            if (obj.jumpMultiplier != 1f) "jump_multiplier" to obj.jumpMultiplier
            if (obj.harvestLevel != -1) "harvest_level" to obj.harvestLevel
            obj.harvestTool?.let { "harvest_tool" to it.name }
            if (obj.isAir) "is_air" to obj.isAir
            if (obj.translationKey != "block.${obj.name}") "translation_key" to obj.translationKey
            if (obj.renderType != "solid") "render_type" to obj.renderType
            if (!obj.opaque) "opaque" to obj.opaque
            //TODO Voxel Shape Serializer
            if (!obj.canDropFromExplosion) "can_drop_from_explosion" to obj.canDropFromExplosion
            if (obj.climbable) "climbable" to obj.climbable
            if (obj.burning) "burning" to obj.burning
            if (!obj.canMobsSpawn) "can_mobs_spawn" to obj.canMobsSpawn
            if (obj.canBeReplacedByLeaves) "can_be_replaced_by_leaves" to obj.canBeReplacedByLeaves
            if (obj.canBeReplacedByLogs) "can_be_replaced_by_logs" to obj.canBeReplacedByLogs
            if (obj.foliage) "foliage" to obj.foliage
            if (obj.fertile) "fertile" to obj.fertile
            if (obj.xpDrop > 0) "xp_drop" to obj.xpDrop
            if (obj.enchantingPowerBonus > 0) "enchanting_power_boost" to obj.enchantingPowerBonus
            if (obj.sticky) "sticky" to obj.sticky
            if (obj.flammability > 0) "flammability" to obj.flammability
            if (obj.fireSpreadSpeed > 0) "fire_spread_speed" to obj.fireSpreadSpeed
            if (obj.fireSource) "fire_source" to obj.fireSource
            if (obj.pushReaction != PushReaction.NORMAL) "push_reaction" to obj.pushReaction.name.toLowerCase()
        }
    }
}