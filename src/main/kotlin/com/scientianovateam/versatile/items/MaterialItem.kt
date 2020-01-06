package com.scientianovateam.versatile.items

import com.scientianovateam.versatile.materialsystem.lists.MaterialItems
import com.scientianovateam.versatile.materialsystem.main.Form
import com.scientianovateam.versatile.materialsystem.main.IMaterialObject
import com.scientianovateam.versatile.materialsystem.main.Material
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.util.text.LanguageMap

//Material Items are Items that have a Material and Object Type
class MaterialItem(override val mat: Material, override val form: Form) : Item(form.itemProperties(mat)), IMaterialObject {
    init {
        registryName = form.registryName(mat)
        MaterialItems.addItem(this)
    }

    override fun getDisplayName(stack: ItemStack) = if (LanguageMap.getInstance().exists(translationKey)) TranslationTextComponent(translationKey) else form.localize(mat)

    override fun getBurnTime(itemStack: ItemStack?) = form.burnTime(mat).let { if (it > 0) it else -1 }
}