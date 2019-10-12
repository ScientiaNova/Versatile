package com.emosewapixel.pixellib.materialsystem.types

import com.blamejared.crafttweaker.api.annotations.ZenRegister
import com.emosewapixel.pixellib.items.MaterialItem
import com.emosewapixel.pixellib.materialsystem.materials.Material
import com.emosewapixel.pixellib.materialsystem.materials.utility.ct.MaterialRequirement
import net.minecraft.item.Item
import net.minecraft.util.text.TranslationTextComponent
import org.openzen.zencode.java.ZenCodeType

@ZenRegister
@ZenCodeType.Name("pixellib.materialsystem.type.ItemType")
class ItemType @JvmOverloads @ZenCodeType.Constructor constructor(name: String, requirement: MaterialRequirement, constructor: (Material, ItemType) -> Item = ::MaterialItem) : ObjectType<Item, ItemType>(name, requirement, constructor) {
    override fun localize(mat: Material) = TranslationTextComponent("itemtype.$name", mat.localizedName)
}