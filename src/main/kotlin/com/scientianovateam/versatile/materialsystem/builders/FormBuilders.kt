package com.scientianovateam.versatile.materialsystem.builders

import com.google.gson.JsonObject
import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.blocks.MaterialBlock
import com.scientianovateam.versatile.blocks.MaterialBlockItem
import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.fluids.IFluidPairHolder
import com.scientianovateam.versatile.fluids.MaterialBucketItem
import com.scientianovateam.versatile.fluids.MaterialFluidBlock
import com.scientianovateam.versatile.fluids.MaterialFluidHolder
import com.scientianovateam.versatile.items.MaterialItem
import com.scientianovateam.versatile.materialsystem.addition.LegacyFormProperties
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.materialsystem.main.Form
import com.scientianovateam.versatile.materialsystem.properties.FormLegacyProperty
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fluids.FluidAttributes

open class FormBuilder(name: String, requirement: (Material) -> Boolean) {
    protected val result = Form(name, requirement)

    fun <T> property(property: FormLegacyProperty<T>, value: T) = this.also { result[property] = value }

    fun blacklistIndices(vararg indices: Int) = this.also { result.indexBlackList += indices.toList() }

    fun bucketVolume(value: Int) = property(LegacyFormProperties.BUCKET_VOLUME, value)

    fun registryNameFunc(value: (Material) -> ResourceLocation) = property(LegacyFormProperties.REGISTRY_NAME_FUM, value)

    fun itemTagName(value: String) = property(LegacyFormProperties.ITEM_TAG, value)

    fun blockTagName(value: String) = property(LegacyFormProperties.BLOCK_TAG, value)

    fun fluidTagName(value: String) = property(LegacyFormProperties.FLUID_TAG, value)

    fun colorFunc(value: (Material) -> Int) = property(LegacyFormProperties.COLOR_FUN, value)

    fun densityMultiplier(value: Float) = property(LegacyFormProperties.DENSITY_MULTIPLIER, value)

    @JvmOverloads
    fun singleTextureType(value: Boolean = true) = property(LegacyFormProperties.SINGLE_TEXTURE_TYPE, value)

    fun burnTimeFunc(value: (Material) -> Int) = property(LegacyFormProperties.BURN_TIME, value)

    fun itemModelFunc(value: (Material) -> JsonObject) = property(LegacyFormProperties.ITEM_MODEL, value)

    fun blockStateFunc(value: (Material) -> JsonObject) = property(LegacyFormProperties.BLOCKSTATE_JSON, value)

    fun itemConstructor(value: (Material, Form) -> Item) = property(LegacyFormProperties.ITEM_CONSTRUCTOR) { mat -> value(mat, result) }

    fun itemPropertiesFunc(value: (Material, Form) -> Item.Properties) = property(LegacyFormProperties.ITEM_PROPERTIES) { mat -> value(mat, result) }

    fun blockConstructor(value: (Material, Form) -> Block) = property(LegacyFormProperties.BLOCK_CONSTRUCTOR) { mat -> value(mat, result) }

    fun blockPropertiesFunc(value: (Material, Form) -> Block.Properties) = property(LegacyFormProperties.BLOCK_PROPERTIES) { mat -> value(mat, result) }

    fun fluidPairConstructor(value: (Material, Form) -> IFluidPairHolder) = property(LegacyFormProperties.FLUID_CONSTRUCTOR) { mat -> value(mat, result) }

    fun fluidAttributesFunc(value: (Material, Form) -> FluidAttributes.Builder) = property(LegacyFormProperties.FLUID_ATTRIBUTES) { mat -> value(mat, result) }

    fun typePriority(value: Int) = property(LegacyFormProperties.TYPE_PRIORITY, value)

    fun buildAndRegister() = result.register()
}

class ItemTypeBuilder(name: String, requirement: (Material) -> Boolean) : FormBuilder(name, requirement) {
    init {
        itemConstructor(::MaterialItem)
    }
}

class BlockTypeBuilder(name: String, requirement: (Material) -> Boolean) : FormBuilder(name, requirement) {
    init {
        itemConstructor(::MaterialBlockItem)
        blockConstructor(::MaterialBlock)
        itemModelFunc { mat ->
            json {
                "parent" to "versatile:block/materialblocks/" + (if (this@BlockTypeBuilder.result.singleTextureType) "" else "${mat.textureSet}/") + name
            }
        }
    }
}

class FluidTypeBuilder(name: String, requirement: (Material) -> Boolean) : FormBuilder(name, requirement) {
    init {
        itemConstructor(::MaterialBucketItem)
        blockConstructor(::MaterialFluidBlock)
        fluidPairConstructor(::MaterialFluidHolder)
        itemPropertiesFunc { _, _ -> Item.Properties().group(Versatile.MAIN).containerItem(Items.BUCKET).maxStackSize(1) }
        blockPropertiesFunc { _, _ -> Block.Properties.from(Blocks.WATER) }
        singleTextureType()
        blacklistIndices(0)
        bucketVolume(1000)
        itemTagName("forge:buckets/$name")
        blockTagName("")
        itemModelFunc { mat ->
            json {
                "parent" to "versatile:block/materialblocks/" + (if (this@FluidTypeBuilder.result.singleTextureType) "" else "${mat.textureSet}/") + name
            }
        }
    }
}