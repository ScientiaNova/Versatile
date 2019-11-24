package com.emosewapixel.pixellib.fluids

import com.emosewapixel.pixellib.extensions.plus
import com.emosewapixel.pixellib.materialsystem.lists.MaterialFluids
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import com.emosewapixel.pixellib.materialsystem.main.IMaterialObject
import com.emosewapixel.pixellib.materialsystem.main.Material
import com.emosewapixel.pixellib.materialsystem.main.ObjectType
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