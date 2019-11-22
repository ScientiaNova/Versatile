package com.emosewapixel.pixellib.materialsystem.types

import com.blamejared.crafttweaker.api.annotations.ZenRegister
import com.emosewapixel.pixellib.extensions.json
import com.emosewapixel.pixellib.fluids.IFluidPairHolder
import com.emosewapixel.pixellib.fluids.MaterialBucketItem
import com.emosewapixel.pixellib.fluids.MaterialFluidBlock
import com.emosewapixel.pixellib.fluids.MaterialFluidHolder
import com.emosewapixel.pixellib.materialsystem.addition.BaseMaterials
import com.emosewapixel.pixellib.materialsystem.elements.ElementUtils
import com.emosewapixel.pixellib.materialsystem.materials.IMaterialObject
import com.emosewapixel.pixellib.materialsystem.materials.Material
import com.emosewapixel.pixellib.materialsystem.materials.utility.ct.MaterialRequirement
import com.google.gson.JsonObject
import net.minecraft.block.FlowingFluidBlock
import net.minecraft.item.BucketItem
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvent
import net.minecraft.util.SoundEvents
import net.minecraft.util.text.TranslationTextComponent
import org.openzen.zencode.java.ZenCodeType
import kotlin.math.min

@ZenRegister
@ZenCodeType.Name("pixellib.materialsystem.types.FluidType")
class FluidType @JvmOverloads @ZenCodeType.Constructor constructor(name: String, requirement: MaterialRequirement, fluidConstructor: (Material, FluidType) -> IFluidPairHolder = ::MaterialFluidHolder, val blockConstructor: (Material, FluidType) -> FlowingFluidBlock = ::MaterialFluidBlock, val bucketConstructor: (Material, FluidType) -> BucketItem = ::MaterialBucketItem) : ObjectType<IFluidPairHolder, FluidType>(name, requirement, fluidConstructor) {
    override fun localize(mat: Material) = TranslationTextComponent("fluidtype.$name", mat.localizedName)

    var overlayTexture: ResourceLocation? = null

    var fillSound: SoundEvent = SoundEvents.ITEM_BUCKET_FILL

    var emptySound: SoundEvent = SoundEvents.ITEM_BUCKET_EMPTY

    @ZenCodeType.Field
    var locationBase = "pixellib:fluid/$name"

    var fluidColor: (Material) -> Int = { color(it) or 0xFF000000.toInt() }

    var buildBlockStateJson: (IMaterialObject) -> JsonObject = {
        json {
            "variants" {
                "" {
                    "model" to "pixellib:block/materialblocks/" + if (BaseMaterials.SINGLE_TEXTURE_TYPE in typeTags) name else "${it.mat.textureType}/$name"
                }
            }
        }
    }
    var temperatureFun: (Material) -> Int = {
        val fluidTemp = it.fluidTemperature
        if (fluidTemp > 0) fluidTemp else 300
    }
    var luminosityFun: (Material) -> Int = { min(15, (temperatureFun(it) - 500) / 50) }
    var gaseousFun: (Material) -> Boolean = Material::isGas
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
        typeTags += BaseMaterials.SINGLE_TEXTURE_TYPE
        indexBlackList += 0
    }
}