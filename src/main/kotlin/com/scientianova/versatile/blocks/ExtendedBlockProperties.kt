package com.scientianova.versatile.blocks

import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.common.ToolType

class ExtendedBlockProperties(
        material: Material,
        materialColor: MaterialColor = material.color,
        blocksMovement: Boolean = true,
        soundType: SoundType = SoundType.STONE,
        lightValue: Int = 0,
        resistance: Float = 0f,
        hardness: Float = 0f,
        ticksRandomly: Boolean = false,
        slipperiness: Float = 0.6f,
        speedFactor: Float = 1.0f,
        jumpFactor: Float = 1.0f,
        isSolid: Boolean = true,
        variableOpacity: Boolean = false,
        harvestLevel: Int = -1,
        harvestTool: ToolType = ToolType.PICKAXE,
        val isAir: Boolean = true,
        val localizedNameFun: Block.() -> ITextComponent? = { TranslationTextComponent(translationKey) },
        val isBurning: Boolean = false,
        val isFoliage: Boolean = false,
        val isBeaconBase: Boolean = false,
        val isConduitFrame: Boolean = false,
        val isPortalFrame: Boolean = false,
        val enchantingBoost: Float = 0f
) : Block.Properties(material, materialColor) {
    init {
        if (!blocksMovement) doesNotBlockMovement()
        sound(soundType)
        lightValue(lightValue)
        hardnessAndResistance(hardness, resistance)
        if (ticksRandomly) tickRandomly()
        slipperiness(slipperiness)
        speedFactor(speedFactor)
        jumpFactor(jumpFactor)
        if (!isSolid) notSolid()
        if (variableOpacity) variableOpacity()
        harvestLevel(harvestLevel)
        harvestTool(harvestTool)
    }
}