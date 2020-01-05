package com.scientianovateam.versatile.blocks

import com.scientianovateam.versatile.materialsystem.lists.MaterialBlocks
import com.scientianovateam.versatile.materialsystem.main.IMaterialObject
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.materialsystem.main.Form
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.util.text.translation.LanguageMap

//Material Blocks are Blocks that have a Material and Object Type
class MaterialBlock(override val mat: Material, override val form: Form) : ModBlock(form.blockProperties(mat), form.registryName(mat).toString()), IMaterialObject {
    init {
        MaterialBlocks.addBlock(this)
    }

    override fun getRenderLayer() = BlockRenderLayer.CUTOUT

    override fun getNameTextComponent() = if (LanguageMap.getInstance().exists(translationKey)) TranslationTextComponent(translationKey) else form.localize(mat)
}