package com.emosewapixel.pixellib.materialsystem.types

import com.emosewapixel.pixellib.items.MaterialItem
import com.emosewapixel.pixellib.materialsystem.materials.Material
import net.minecraft.item.Item
import net.minecraft.util.text.TranslationTextComponent

class ItemType @JvmOverloads constructor(name: String, requirement: (Material) -> Boolean, constructor: (Material, ItemType) -> Item = ::MaterialItem) : ObjectType<Item, ItemType>(name, requirement, constructor) {
    override fun localize(mat: Material) = TranslationTextComponent("itemtype.$name", mat.localizedName)
}