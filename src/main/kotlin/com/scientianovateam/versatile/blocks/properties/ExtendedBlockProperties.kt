package com.scientianovateam.versatile.blocks.properties

import com.google.gson.JsonObject
import com.scientianovateam.versatile.blocks.BLOCK_MATERIALS
import com.scientianovateam.versatile.blocks.SOUND_TYPES
import com.scientianovateam.versatile.common.extensions.*
import com.scientianovateam.versatile.common.serialization.IJSONSerializer
import net.minecraft.block.Block
import net.minecraft.block.material.MaterialColor
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.util.math.shapes.VoxelShapes
import net.minecraftforge.common.ToolType

open class ExtendedBlockProperties(
        val material: BlockMaterialWrapper,
        val materialColor: MaterialColor = material.color,
        val blocksMovement: Boolean = material.blocksMovement,
        val sound: SoundTypeV = SOUND_TYPES["stone".toResLoc()]!!,
        val lightValue: Int = 0,
        val hardness: Float = 0f,
        val resistance: Float = 0f,
        val ticksRandomly: Boolean = false,
        val slipperiness: Float = .6f,
        val harvestLevel: Int = -1,
        val harvestTool: ToolType? = null,
        val isAir: Boolean = false,
        val translationKey: String? = null,
        val renderLayer: BlockRenderLayer = BlockRenderLayer.SOLID,
        val solid: Boolean = blocksMovement && renderLayer == BlockRenderLayer.SOLID,
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
        val fireSource: Boolean = false
) : Block.Properties(material.material, materialColor) {
    init {
        if (!blocksMovement) super.doesNotBlockMovement()
        super.sound(sound)
        super.lightValue(lightValue)
        super.hardnessAndResistance(hardness, resistance)
        if (ticksRandomly) super.tickRandomly()
        super.slipperiness(slipperiness)
        super.harvestLevel(harvestLevel)
        if (harvestTool != null) super.harvestTool(harvestTool)
    }

    object Serializer : IJSONSerializer<ExtendedBlockProperties, JsonObject> {
        override fun read(json: JsonObject): ExtendedBlockProperties {
            val material = json.getStringOrNull("material")?.let { BLOCK_MATERIALS[it.toResLoc()] }
                    ?: error("Missing block material")
            val blocksMovement = json.getBooleanOrNull("blocks_movement") ?: material.blocksMovement
            val renderLayer = json.getStringOrNull("render_layer")?.let { BlockRenderLayer.valueOf(it.toUpperCase()) }
                    ?: BlockRenderLayer.SOLID
            return ExtendedBlockProperties(
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
                    harvestLevel = json.getIntOrNull("harvest_level") ?: -1,
                    harvestTool = json.getStringOrNull("harvest_tool")?.let(ToolType::get),
                    isAir = json.getBooleanOrNull("is_air") ?: false,
                    translationKey = json.getStringOrNull("translation_key"),
                    renderLayer = renderLayer,
                    solid = json.getBooleanOrNull("solid") ?: blocksMovement && renderLayer == BlockRenderLayer.SOLID,
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
                    fireSpreadSpeed = json.getIntOrNull("fire_spread_seped") ?: 0,
                    fireSource = json.getBooleanOrNull("fire_source") ?: false
            )
        }

        override fun write(obj: ExtendedBlockProperties) = json {
            "material" to obj.material.registryName
            "material_color" to obj.materialColor
            "blocks_movement" to obj.blocksMovement
            "sound" to obj.sound.registryName
            "light_value" to obj.lightValue
            "hardness" to obj.hardness
            "resistance" to obj.resistance
            "ticks_randomly" to obj.ticksRandomly
            "slipperiness" to obj.slipperiness
            "harvest_level" to obj.harvestLevel
            obj.harvestTool?.let { "harvest_tool" to it.name }
            "is_air" to obj.isAir
            obj.translationKey?.let { "translation_key" to it }
            "render_layer" to obj.renderLayer.name.toLowerCase()
            "solid" to obj.solid
            //TODO Voxel Shape Serializer
            "can_drop_from_explosion" to obj.canDropFromExplosion
            "climbable" to obj.climbable
            "burning" to obj.burning
            "can_mobs_spawn" to obj.canMobsSpawn
            "can_be_replaced_by_leaves" to obj.canBeReplacedByLeaves
            "can_be_replaced_by_logs" to obj.canBeReplacedByLogs
            "foliage" to obj.foliage
            "fertile" to obj.fertile
            "xp_drop" to obj.xpDrop
            "enchanting_power_boost" to obj.enchantingPowerBonus
            "sticky" to obj.sticky
            "flammability" to obj.flammability
            "fire_spread_speed" to obj.fireSpreadSpeed
            "fire_source" to obj.fireSource
        }
    }
}