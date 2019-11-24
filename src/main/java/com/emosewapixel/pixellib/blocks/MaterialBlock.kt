package com.emosewapixel.pixellib.blocks

import com.emosewapixel.pixellib.materialsystem.lists.MaterialBlocks
import com.emosewapixel.pixellib.materialsystem.main.IMaterialObject
import com.emosewapixel.pixellib.materialsystem.main.Material
import com.emosewapixel.pixellib.materialsystem.main.ObjectType
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.util.text.translation.LanguageMap

//Material Blocks are Blocks that have a Material and Object Type
class MaterialBlock(override val mat: Material, override val objType: ObjectType) : ModBlock(objType.blockProperties(mat), objType.buildRegistryName(mat).toString()), IMaterialObject {
    init {
        MaterialBlocks.addBlock(this)
    }

    override fun getRenderLayer() = BlockRenderLayer.CUTOUT

    override fun getNameTextComponent() = if (LanguageMap.getInstance().exists(translationKey)) TranslationTextComponent(translationKey) else objType.localize(mat)
}