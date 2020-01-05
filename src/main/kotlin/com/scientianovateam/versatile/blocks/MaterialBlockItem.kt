package com.scientianovateam.versatile.blocks

import com.scientianovateam.versatile.materialsystem.lists.MaterialBlocks
import com.scientianovateam.versatile.materialsystem.lists.MaterialItems
import com.scientianovateam.versatile.materialsystem.main.Form
import com.scientianovateam.versatile.materialsystem.main.IMaterialObject
import com.scientianovateam.versatile.materialsystem.main.Material
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.util.text.ITextComponent

open class MaterialBlockItem(override val mat: Material, override val form: Form) : BlockItem(MaterialBlocks[mat, form]!!, form.itemProperties(mat)), IMaterialObject {
    init {
        registryName = block.registryName
        MaterialItems.addItem(this)
    }

    override fun getDisplayName(stack: ItemStack): ITextComponent = block.nameTextComponent

    override fun getBurnTime(itemStack: ItemStack?) = form.burnTime(mat).let { if (it > 0) it else -1 }
}