package com.scientianova.versatile.blocks

import com.scientianova.versatile.materialsystem.lists.MaterialBlocks
import com.scientianova.versatile.materialsystem.lists.MaterialItems
import com.scientianova.versatile.materialsystem.main.IMaterialObject
import com.scientianova.versatile.materialsystem.main.Material
import com.scientianova.versatile.materialsystem.main.ObjectType
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