package com.emosewapixel.pixellib.materialsystem.builders

import com.emosewapixel.pixellib.PixelLib
import com.emosewapixel.pixellib.blocks.MaterialBlock
import com.emosewapixel.pixellib.blocks.MaterialBlockItem
import com.emosewapixel.pixellib.extensions.json
import com.emosewapixel.pixellib.fluids.IFluidPairHolder
import com.emosewapixel.pixellib.fluids.MaterialBucketItem
import com.emosewapixel.pixellib.fluids.MaterialFluidBlock
import com.emosewapixel.pixellib.fluids.MaterialFluidHolder
import com.emosewapixel.pixellib.items.MaterialItem
import com.emosewapixel.pixellib.materialsystem.main.Material
import com.emosewapixel.pixellib.materialsystem.main.ObjectType
import com.emosewapixel.pixellib.materialsystem.main.ct.MaterialRequirement
import com.emosewapixel.pixellib.materialsystem.addition.ObjTypeProperties
import com.emosewapixel.pixellib.materialsystem.properties.ObjTypeProperty
import com.google.gson.JsonObject
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fluids.FluidAttributes

open class ObjTypeBuilder(name: String, requirement: MaterialRequirement) {
    protected val result = ObjectType(name, requirement)

    fun <T> property(property: ObjTypeProperty<T>, value: T) = this.also { result[property] = value }

    fun blacklistIndices(vararg indices: Int) = this.also { result.indexBlackList += indices.toList() }

    fun bucketVolume(value: Int) = property(ObjTypeProperties.BUCKET_VOLUME, value)

    fun registryNameFunc(value: (Material) -> ResourceLocation) = property(ObjTypeProperties.REGISTRY_NAME_FUM, value)

    fun itemTagName(value: String) = property(ObjTypeProperties.ITEM_TAG, value)

    fun blockTagName(value: String) = property(ObjTypeProperties.BLOCK_TAG, value)

    fun fluidTagName(value: String) = property(ObjTypeProperties.FLUID_TAG, value)

    fun colorFunc(value: (Material) -> Int) = property(ObjTypeProperties.COLOR_FUN, value)

    fun densityMultiplier(value: Float) = property(ObjTypeProperties.DENSITY_MULTIPLIER, value)

    fun isGasFunc(value: (Material) -> Boolean) = property(ObjTypeProperties.IS_GAS, value)

    fun temperatureFunc(value: (Material) -> Int) = property(ObjTypeProperties.TEMPERATURE, value)

    @JvmOverloads
    fun singleTextureType(value: Boolean = true) = property(ObjTypeProperties.SINGLE_TEXTURE_TYPE, value)

    fun burnTimeFunc(value: (Material) -> Int) = property(ObjTypeProperties.BURN_TIME, value)

    fun itemModelFunc(value: (Material) -> JsonObject) = property(ObjTypeProperties.ITEM_MODEL, value)

    fun blockStateFunc(value: (Material) -> JsonObject) = property(ObjTypeProperties.BLOCKSTATE_JSON, value)

    fun itemConstructor(value: (Material, ObjectType) -> Item) = property(ObjTypeProperties.ITEM_CONSTRUCTOR) { mat -> value(mat, result) }

    fun itemPropertiesFunc(value: (Material, ObjectType) -> Item.Properties) = property(ObjTypeProperties.ITEM_PROPERTIES) { mat -> value(mat, result) }

    fun blockConstructor(value: (Material, ObjectType) -> Block) = property(ObjTypeProperties.BLOCK_CONSTRUCTOR) { mat -> value(mat, result) }

    fun blockPropertiesFunc(value: (Material, ObjectType) -> Block.Properties) = property(ObjTypeProperties.BLOCK_PROPERTIES) { mat -> value(mat, result) }

    fun fluidPairConstructor(value: (Material, ObjectType) -> IFluidPairHolder) = property(ObjTypeProperties.FLUID_CONSTRUCTOR) { mat -> value(mat, result) }

    fun fluidAttributesFunc(value: (Material, ObjectType) -> FluidAttributes.Builder) = property(ObjTypeProperties.FLUID_ATTRIBUTES) { mat -> value(mat, result) }

    fun typePriority(value: Int) = property(ObjTypeProperties.TYPE_PRIORITY, value)

    fun buildAndRegister() = result.register()
}

class ItemTypeBuilder(name: String, requirement: MaterialRequirement) : ObjTypeBuilder(name, requirement) {
    init {
        itemConstructor(::MaterialItem)
    }
}

class BlockTypeBuilder(name: String, requirement: MaterialRequirement) : ObjTypeBuilder(name, requirement) {
    init {
        itemConstructor(::MaterialBlockItem)
        blockConstructor(::MaterialBlock)
        itemModelFunc { mat ->
            json {
                "parent" to "pixellib:block/materialblocks/" + (if (this@BlockTypeBuilder.result.singleTextureType) "" else "${mat.textureType}/") + name
            }
        }
    }
}

class FluidTypeBuilder(name: String, requirement: MaterialRequirement) : ObjTypeBuilder(name, requirement) {
    init {
        itemConstructor(::MaterialBucketItem)
        blockConstructor(::MaterialFluidBlock)
        fluidPairConstructor(::MaterialFluidHolder)
        itemPropertiesFunc { _, _ -> Item.Properties().group(PixelLib.MAIN).containerItem(Items.BUCKET).maxStackSize(1) }
        blockPropertiesFunc { _, _ -> Block.Properties.from(Blocks.WATER) }
        singleTextureType()
        blacklistIndices(0)
        bucketVolume(1000)
        itemTagName("forge:buckets/$name")
        blockTagName("")
        itemModelFunc { mat ->
            json {
                "parent" to "pixellib:block/materialblocks/" + (if (this@FluidTypeBuilder.result.singleTextureType) "" else "${mat.textureType}/") + name
            }
        }
    }
}