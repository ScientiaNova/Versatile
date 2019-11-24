package com.emosewapixel.pixellib.blocks

import com.emosewapixel.pixellib.materialsystem.lists.MaterialBlocks
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import com.emosewapixel.pixellib.materialsystem.main.IMaterialObject
import com.emosewapixel.pixellib.materialsystem.main.Material
import com.emosewapixel.pixellib.materialsystem.main.ObjectType
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.util.text.ITextComponent

open class MaterialBlockItem(override val mat: Material, override val objType: ObjectType) : BlockItem(MaterialBlocks[mat, objType]!!, objType.itemProperties(mat)), IMaterialObject {
    init {
        registryName = block.registryName
        MaterialItems.addItem(this)
    }

    override fun getDisplayName(stack: ItemStack): ITextComponent = block.nameTextComponent
}