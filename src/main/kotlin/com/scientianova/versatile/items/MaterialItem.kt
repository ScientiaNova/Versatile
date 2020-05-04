package com.scientianova.versatile.items

import com.scientianova.versatile.materialsystem.lists.MaterialItems
import com.scientianova.versatile.materialsystem.main.IMaterialObject
import com.scientianova.versatile.materialsystem.main.Material
import com.scientianova.versatile.materialsystem.main.ObjectType
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.text.LanguageMap
import net.minecraft.util.text.TranslationTextComponent

//Material Items are Items that have a Material and Object Type
class MaterialItem(override val mat: Material, override val objType: ObjectType) : Item(objType.itemProperties(mat)), IMaterialObject {
    init {
        registryName = objType.registryName(mat)
        MaterialItems.addItem(this)
    }

    override fun getDisplayName(stack: ItemStack) = if (LanguageMap.getInstance().exists(translationKey)) TranslationTextComponent(translationKey) else objType.localize(mat)
}