package com.scientianova.versatile.blocks

import com.scientianovateam.versatile.materialsystem.lists.MaterialBlocks
import com.scientianovateam.versatile.materialsystem.main.IMaterialObject
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.materialsystem.main.ObjectType
import net.minecraft.util.text.LanguageMap
import net.minecraft.util.text.TranslationTextComponent

//Material Blocks are Blocks that have a Material and Object Type
class MaterialBlock(override val mat: Material, override val objType: ObjectType) : _root_ide_package_.com.scientianova.versatile.blocks.ModBlock(objType.blockProperties(mat), objType.registryName(mat).toString()), IMaterialObject {
    init {
        MaterialBlocks.addBlock(this)
    }

    override fun getNameTextComponent() = if (LanguageMap.getInstance().exists(translationKey)) TranslationTextComponent(translationKey) else objType.localize(mat)
}