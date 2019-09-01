package com.EmosewaPixel.pixellib.items

import com.EmosewaPixel.pixellib.PixelLib
import com.EmosewaPixel.pixellib.materialsystem.lists.MaterialItems
import com.EmosewaPixel.pixellib.materialsystem.materials.IMaterialItem
import com.EmosewaPixel.pixellib.materialsystem.materials.Material
import com.EmosewaPixel.pixellib.materialsystem.types.ObjectType
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.util.text.translation.LanguageMap

//Material Items are Items that have a Material and Object Type
class MaterialItem(override val mat: Material, override val objType: ObjectType) : Item(Item.Properties().group(PixelLib.main)), IMaterialItem {

    init {
        setRegistryName("pixellib:" + mat.name + "_" + objType.name)
        MaterialItems.addItem(this)
    }

    override fun getDisplayName(stack: ItemStack) = if (LanguageMap.getInstance().exists(translationKey)) TranslationTextComponent(translationKey) else TranslationTextComponent("itemtype." + objType.name, mat.translationKey)
}