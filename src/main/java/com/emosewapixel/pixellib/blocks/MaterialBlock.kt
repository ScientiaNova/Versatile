package com.emosewapixel.pixellib.blocks

import com.emosewapixel.pixellib.materialsystem.lists.MaterialBlocks
import com.emosewapixel.pixellib.materialsystem.materials.IMaterialObject
import com.emosewapixel.pixellib.materialsystem.materials.Material
import com.emosewapixel.pixellib.materialsystem.types.BlockType
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.util.text.translation.LanguageMap

//Material Blocks are Blocks that have a Material and Object Type
class MaterialBlock(override val mat: Material, override val objType: BlockType) : ModBlock(objType.properties.hardnessAndResistance(mat.harvestTier.hardness, mat.harvestTier.resistance), objType.buildRegistryName(mat).toString(), mat.harvestTier.harvestLevel), IMaterialObject {
    init {
        MaterialBlocks.addBlock(this)
    }

    override fun getRenderLayer() = BlockRenderLayer.CUTOUT

    override fun getNameTextComponent() = if (LanguageMap.getInstance().exists(translationKey)) TranslationTextComponent(translationKey) else objType.localize(mat)
}