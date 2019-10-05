package com.emosewapixel.pixellib.materialsystem.types

import com.emosewapixel.pixellib.extensions.json
import com.emosewapixel.pixellib.fluids.IFluidPairHolder
import com.emosewapixel.pixellib.fluids.MaterialBucketItem
import com.emosewapixel.pixellib.fluids.MaterialFluidBlock
import com.emosewapixel.pixellib.fluids.MaterialFluidHolder
import com.emosewapixel.pixellib.materialsystem.addition.MaterialRegistry
import com.emosewapixel.pixellib.materialsystem.element.ElementUtils
import com.emosewapixel.pixellib.materialsystem.materials.DustMaterial
import com.emosewapixel.pixellib.materialsystem.materials.FluidMaterial
import com.emosewapixel.pixellib.materialsystem.materials.IMaterialObject
import com.emosewapixel.pixellib.materialsystem.materials.Material
import com.google.gson.JsonObject
import net.minecraft.block.FlowingFluidBlock
import net.minecraft.fluid.Fluids
import net.minecraft.item.BucketItem
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvent
import net.minecraft.util.SoundEvents
import net.minecraft.util.text.TranslationTextComponent
import kotlin.math.min

class FluidType(name: String, requirement: (Material) -> Boolean, fluidConstructor: (Material, FluidType) -> IFluidPairHolder = ::MaterialFluidHolder, val blockConstructor: (Material, FluidType) -> FlowingFluidBlock = ::MaterialFluidBlock, val bucketConstructor: (Material, FluidType) -> BucketItem = ::MaterialBucketItem) : ObjectType<IFluidPairHolder, FluidType>(name, requirement, fluidConstructor) {
    override fun localize(mat: Material) = TranslationTextComponent("fluidtype.$name", mat.localizedName)

    var overlayTexture: ResourceLocation? = null
    var fillSound: SoundEvent = SoundEvents.ITEM_BUCKET_FILL
    var emptySound: SoundEvent = SoundEvents.ITEM_BUCKET_EMPTY
    var locationBase = "pixellib:fluid/$name"
    var fluidColor: (Material) -> Int = { color(it) or 0xFF000000.toInt() }

    var buildBlockStateJson: (IMaterialObject) -> JsonObject = {
        json {
            "variants" {
                "" {
                    "model" to "pixellib:block/materialblocks/" + if (MaterialRegistry.SINGLE_TEXTURE_TYPE in typeTags) name else "${it.mat.textureType}/$name"
                }
            }
        }
    }
    var temperatureFun: (Material) -> Int = {
        when (it) {
            is DustMaterial -> it.meltingTemperature
            is FluidMaterial -> it.temperature
            else -> Fluids.WATER.attributes.temperature
        }
    }
    var luminosityFun: (Material) -> Int = { min(15, (temperatureFun(it) - 500) / 50) }
    var gaseousFun: (Material) -> Boolean = { it.hasTag(MaterialRegistry.IS_GAS) }
    var densityFun: (Material) -> Int = {
        val density = ElementUtils.getTotalDensity(it).toInt() * densityMultiplier
        if (gaseousFun(it))
            -density.toInt()
        else
            density.toInt()
    }
    var viscosityFun: (Material) -> Int = { ElementUtils.getTotalDensity(it).toInt() }

    init {
        bucketVolume = 1000
        typeTags += MaterialRegistry.SINGLE_TEXTURE_TYPE
        indexBlackList += 0
    }
}