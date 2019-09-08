package com.emosewapixel.pixellib.items

import com.emosewapixel.pixellib.PixelLib
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import com.emosewapixel.pixellib.materialsystem.materials.IMaterialItem
import com.emosewapixel.pixellib.materialsystem.materials.Material
import com.emosewapixel.pixellib.materialsystem.types.ObjectType
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.util.text.translation.LanguageMap

//Material Items are Items that have a Material and Object Type
open class MaterialItem(override val mat: Material, override val objType: ObjectType) : Item(Properties().group(PixelLib.main)), IMaterialItem {
    init {
        setRegistryName("pixellib:" + mat.name + "_" + objType.name)
        MaterialItems.addItem(this)
    }

    override fun getDisplayName(stack: ItemStack) = if (LanguageMap.getInstance().exists(translationKey)) TranslationTextComponent(translationKey) else TranslationTextComponent("itemtype." + objType.name, mat.translationKey)
}