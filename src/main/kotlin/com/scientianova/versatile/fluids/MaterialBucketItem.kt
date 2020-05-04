package com.scientianova.versatile.fluids

import com.scientianova.versatile.common.extensions.plus
import com.scientianova.versatile.materialsystem.lists.MaterialFluids
import com.scientianova.versatile.materialsystem.lists.MaterialItems
import com.scientianova.versatile.materialsystem.main.IMaterialObject
import com.scientianova.versatile.materialsystem.main.Material
import com.scientianova.versatile.materialsystem.main.ObjectType
import net.minecraft.item.BucketItem
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper

open class MaterialBucketItem(final override val mat: Material, final override val objType: ObjectType) : BucketItem({ MaterialFluids[mat, objType] }, objType.itemProperties(mat)), IMaterialObject {
    init {
        registryName = objType.registryName(mat) + "_bucket"
        MaterialItems.addItem(this)
    }

    override fun getDisplayName(stack: ItemStack) = TranslationTextComponent("container.bucket", objType.localize(mat))

    override fun initCapabilities(stack: ItemStack, nbt: CompoundNBT?) = FluidBucketWrapper(stack)
}