package com.scientianovateam.versatile.items

import com.scientianovateam.versatile.materialsystem.lists.MaterialItems
import com.scientianovateam.versatile.materialsystem.main.IMaterialObject
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.materialsystem.main.Form
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.util.text.translation.LanguageMap

//Material Items are Items that have a Material and Object Type
class MaterialItem(override val mat: Material, override val objType: Form) : Item(objType.itemProperties(mat)), IMaterialObject {
    init {
        registryName = objType.registryName(mat)
        MaterialItems.addItem(this)
    }

    override fun getDisplayName(stack: ItemStack) = if (LanguageMap.getInstance().exists(translationKey)) TranslationTextComponent(translationKey) else objType.localize(mat)
}