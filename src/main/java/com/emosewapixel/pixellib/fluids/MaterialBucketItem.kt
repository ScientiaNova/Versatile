package com.emosewapixel.pixellib.fluids

import com.emosewapixel.pixellib.PixelLib
import com.emosewapixel.pixellib.materialsystem.lists.MaterialFluids
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import com.emosewapixel.pixellib.materialsystem.materials.IMaterialObject
import com.emosewapixel.pixellib.materialsystem.materials.Material
import com.emosewapixel.pixellib.materialsystem.types.ObjectType
import net.minecraft.item.BucketItem
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper
import com.emosewapixel.pixellib.extensions.plus

class MaterialBucketItem(override val mat: Material, override val objType: ObjectType<*, *>) : BucketItem({ MaterialFluids[mat, objType] }, Properties().group(PixelLib.MAIN).containerItem(Items.BUCKET).maxStackSize(0)), IMaterialObject {
    init {
        registryName = objType.buildRegistryName(mat) + "_bucket"
        MaterialItems.addItem(this)
    }

    override fun getDisplayName(stack: ItemStack) = TranslationTextComponent("container.bucket", objType.localize(mat))

    override fun initCapabilities(stack: ItemStack, nbt: CompoundNBT?) = FluidBucketWrapper(stack)
}