package com.scientianova.versatile.fluids

import com.scientianova.versatile.materialsystem.main.Material
import com.scientianova.versatile.materialsystem.main.Form
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

    override fun initCapabilities(stack: ItemStack, nbt: CompoundNBT?) = FluidBucketWrapper(stack)
}