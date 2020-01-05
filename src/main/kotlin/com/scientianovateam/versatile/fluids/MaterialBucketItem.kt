package com.scientianovateam.versatile.fluids

import com.scientianovateam.versatile.common.extensions.plus
import com.scientianovateam.versatile.materialsystem.lists.MaterialFluids
import com.scientianovateam.versatile.materialsystem.lists.MaterialItems
import com.scientianovateam.versatile.materialsystem.main.Form
import com.scientianovateam.versatile.materialsystem.main.IMaterialObject
import com.scientianovateam.versatile.materialsystem.main.Material
import net.minecraft.item.BucketItem
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper

open class MaterialBucketItem(final override val mat: Material, final override val form: Form) : BucketItem({ MaterialFluids[mat, form] }, form.itemProperties(mat)), IMaterialObject {
    init {
        registryName = form.registryName(mat) + "_bucket"
        MaterialItems.addItem(this)
    }

    override fun getDisplayName(stack: ItemStack) = TranslationTextComponent("container.bucket", form.localize(mat))

    override fun getBurnTime(itemStack: ItemStack?) = form.burnTime(mat).let { if (it > 0) it else -1 }

    override fun initCapabilities(stack: ItemStack, nbt: CompoundNBT?) = FluidBucketWrapper(stack)
}