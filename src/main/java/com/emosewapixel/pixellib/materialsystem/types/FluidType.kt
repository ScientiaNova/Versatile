package com.emosewapixel.pixellib.materialsystem.types

import com.emosewapixel.pixellib.fluids.IFluidPairHolder
import com.emosewapixel.pixellib.fluids.MaterialBucketItem
import com.emosewapixel.pixellib.fluids.MaterialFluidBlock
import com.emosewapixel.pixellib.fluids.MaterialFluidHolder
import com.emosewapixel.pixellib.materialsystem.MaterialRegistry
import com.emosewapixel.pixellib.materialsystem.materials.DustMaterial
import com.emosewapixel.pixellib.materialsystem.materials.FluidMaterial
import com.emosewapixel.pixellib.materialsystem.materials.Material
import net.minecraft.block.FlowingFluidBlock
import net.minecraft.fluid.Fluids
import net.minecraft.item.BucketItem
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvent
import net.minecraft.util.SoundEvents
import net.minecraft.util.text.TranslationTextComponent

class FluidType(name: String, requirement: (Material) -> Boolean, fluidConstructor: (Material, FluidType) -> IFluidPairHolder = ::MaterialFluidHolder, val blockConstructor: (Material, FluidType) -> FlowingFluidBlock = ::MaterialFluidBlock, val bucketConstructor: (Material, FluidType) -> BucketItem = ::MaterialBucketItem) : ObjectType<IFluidPairHolder, FluidType>(name, requirement, fluidConstructor) {
    override fun localize(mat: Material) = TranslationTextComponent("fluidtype.$name", mat.localizedName)

    var overlayTexture: ResourceLocation? = null
    var fillSound: SoundEvent = SoundEvents.ITEM_BUCKET_FILL
    var emptySound: SoundEvent = SoundEvents.ITEM_BUCKET_EMPTY
    var locationBase = "pixellib:fluid/$name"

    var temperatureFun: (Material) -> Int = {
        when (it) {
            is DustMaterial -> it.meltingTemperature
            is FluidMaterial -> it.temperature
            else -> Fluids.WATER.attributes.temperature
        }
    }

    init {
        bucketVolume = 1000
        typeTags += MaterialRegistry.SINGLE_TEXTURE_TYPE
        indexBlackList += 0
    }
}