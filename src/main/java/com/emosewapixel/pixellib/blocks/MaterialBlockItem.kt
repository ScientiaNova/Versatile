package com.emosewapixel.pixellib.blocks

import com.emosewapixel.pixellib.PixelLib
import com.emosewapixel.pixellib.materialsystem.lists.MaterialBlocks
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import com.emosewapixel.pixellib.materialsystem.materials.IMaterialObject
import com.emosewapixel.pixellib.materialsystem.materials.Material
import com.emosewapixel.pixellib.materialsystem.types.ObjectType
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.util.text.ITextComponent

class MaterialBlockItem(override val mat: Material, override val objType: ObjectType<*, *>) : BlockItem(MaterialBlocks[mat, objType]!!, Properties().group(PixelLib.MAIN)), IMaterialObject {
    init {
        registryName = block.registryName
        MaterialItems.addItem(this)
    }

    override fun getDisplayName(stack: ItemStack): ITextComponent = block.nameTextComponent
}