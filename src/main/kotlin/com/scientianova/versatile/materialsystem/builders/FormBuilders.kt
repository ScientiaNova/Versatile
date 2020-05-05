package com.scientianova.versatile.materialsystem.builders

import com.google.gson.JsonObject
import com.scientianova.versatile.Versatile
import com.scientianova.versatile.common.extensions.json
import com.scientianova.versatile.fluids.IFluidPairHolder
import com.scientianova.versatile.fluids.MaterialBucketItem
import com.scientianova.versatile.fluids.MaterialFluidBlock
import com.scientianova.versatile.fluids.MaterialFluidHolder
import com.scientianova.versatile.materialsystem.addition.ObjTypeProperties
import com.scientianova.versatile.materialsystem.main.Material
import com.scientianova.versatile.materialsystem.main.Form
import com.scientianova.versatile.materialsystem.properties.FormProperty
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fluids.FluidAttributes

open class FormBuilder(name: String, requirement: (Material) -> Boolean) {
    protected val result = Form(name, requirement)

    fun <T> property(property: FormProperty<T>, value: T) = this.also { result[property] = value }

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

    fun itemConstructor(value: (Material, Form) -> Item) = property(ObjTypeProperties.ITEM_CONSTRUCTOR) { mat -> value(mat, result) }

    fun itemPropertiesFunc(value: (Material, Form) -> Item.Properties) = property(ObjTypeProperties.ITEM_PROPERTIES) { mat -> value(mat, result) }

    fun blockConstructor(value: (Material, Form) -> Block) = property(ObjTypeProperties.BLOCK_CONSTRUCTOR) { mat -> value(mat, result) }

    fun blockPropertiesFunc(value: (Material, Form) -> Block.Properties) = property(ObjTypeProperties.BLOCK_PROPERTIES) { mat -> value(mat, result) }

    fun fluidPairConstructor(value: (Material, Form) -> IFluidPairHolder) = property(ObjTypeProperties.FLUID_CONSTRUCTOR) { mat -> value(mat, result) }

    fun fluidAttributesFunc(value: (Material, Form) -> FluidAttributes.Builder) = property(ObjTypeProperties.FLUID_ATTRIBUTES) { mat -> value(mat, result) }

    fun typePriority(value: Int) = property(ObjTypeProperties.TYPE_PRIORITY, value)

    fun buildAndRegister() = result.register()
}

class ItemFormBuilder(name: String, requirement: (Material) -> Boolean) : FormBuilder(name, requirement) {
    init {
        itemConstructor(::MaterialItem)
    }
}

class BlockFormBuilder(name: String, requirement: (Material) -> Boolean) : FormBuilder(name, requirement) {
    init {
        itemConstructor(::MaterialBlockItem)
        blockConstructor(::MaterialBlock)
        itemModelFunc { mat ->
            json {
                "parent" to "versatile:block/materialblocks/" + (if (this@BlockFormBuilder.result.singleTextureSet) "" else "${mat.textureSet}/") + name
            }
        }
    }
}

class FluidFormBuilder(name: String, requirement: (Material) -> Boolean) : FormBuilder(name, requirement) {
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
                "parent" to "versatile:block/materialblocks/" + (if (this@FluidFormBuilder.result.singleTextureSet) "" else "${mat.textureSet}/") + name
            }
        }
    }
}