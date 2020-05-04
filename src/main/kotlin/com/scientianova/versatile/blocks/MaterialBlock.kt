package com.scientianova.versatile.blocks

import com.scientianova.versatile.materialsystem.lists.MaterialBlocks
import com.scientianova.versatile.materialsystem.main.IMaterialObject
import com.scientianova.versatile.materialsystem.main.Material
import com.scientianova.versatile.materialsystem.main.Form
import net.minecraft.util.text.LanguageMap
import net.minecraft.util.text.TranslationTextComponent

//Material Blocks are Blocks that have a Material and Object Type
class MaterialBlock(override val mat: Material, override val form: Form) : ModBlock(form.blockProperties(mat), form.registryName(mat).toString()), IMaterialObject {
    init {
        MaterialBlocks.addBlock(this)
    }

    override fun getNameTextComponent() = if (LanguageMap.getInstance().exists(translationKey)) TranslationTextComponent(translationKey) else form.localize(mat)
}