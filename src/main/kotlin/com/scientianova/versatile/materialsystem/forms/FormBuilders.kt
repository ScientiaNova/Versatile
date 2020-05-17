package com.scientianova.versatile.materialsystem.forms

import com.google.gson.JsonObject
import com.scientianova.versatile.Versatile
import com.scientianova.versatile.blocks.ExtendedBlockProperties
import com.scientianova.versatile.blocks.VersatileBlock
import com.scientianova.versatile.blocks.VersatileBlockItem
import com.scientianova.versatile.common.extensions.json
import com.scientianova.versatile.fluids.VersatileBucketItem
import com.scientianova.versatile.fluids.VersatileFluidBlock
import com.scientianova.versatile.items.ExtendedItemProperties
import com.scientianova.versatile.items.VersatileItem
import com.scientianova.versatile.materialsystem.materials.Material
import com.scientianova.versatile.materialsystem.properties.*
import net.minecraft.block.Block
import net.minecraft.fluid.FlowingFluid
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.LanguageMap
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.fluids.ForgeFlowingFluid

open class FormBuilder(name: String) {
    private val result = GlobalForm()

    init {
        result.name = name
    }

    fun <T> property(property: GlobalFormProperty<T>, value: T) = this.also { result[property] = value }

    fun <T> property(property: FormProperty<T>, value: Form.() -> T) = this.also { result[property] = value }

    fun predicate(value: (Material) -> Boolean) = property(PREDICATE, value)

    fun blacklistIndices(vararg indices: Int) = property(INDEX_BLACKLIST, indices.toList())

    fun bucketVolume(value: Int) = property(BUCKET_VOLUME, value)

    fun registryNameFunc(value: (Form) -> ResourceLocation) = property(REGISTRY_NAME, value)

    fun itemTagName(value: String) = property(ITEM_TAG, value)

    fun blockTagName(value: String) = property(BLOCK_TAG, value)

    fun combinedItemTagNames(value: (Form) -> List<String>) = property(COMBINED_ITEM_TAGS, value)

    fun combinedBlockTagNames(value: (Form) -> List<String>) = property(COMBINED_BLOCK_TAGS, value)

    fun combinedFluidTagNames(value: (Form) -> List<String>) = property(COMBINED_FLUID_TAGS, value)

    fun colorFunc(value: (Form) -> Int) = property(FORM_COLOR, value)

    fun densityMultiplier(value: (Form) -> Float) = property(FORM_DENSITY_MULTIPLIER, value)

    fun singleTextureSet() = property(SINGLE_TEXTURE_SET, true)

    fun burnTimeFunc(value: (Form) -> Int) = property(BURN_TIME, value)

    fun itemModelFunc(value: (Form) -> JsonObject) = property(ITEM_MODEL, value)

    fun blockStateFunc(value: (Form) -> JsonObject) = property(BLOCKSTATE_JSON, value)

    fun itemConstructor(value: (Form) -> Item) = property(ITEM, value)

    fun blockConstructor(value: (Form) -> Block) = property(BLOCK, value)

    fun fluidAttributesFunc(value: (Form) -> ForgeFlowingFluid.Properties) = property(FLUID_PROPERTIES, value)

    fun stillFluid(value: (Form) -> FlowingFluid) = property(STILL_FLUID, value)

    fun flowingFluid(value: (Form) -> FlowingFluid) = property(FLOWING_FLUID, value)

    fun build() = result
}

class ItemFormBuilder(name: String) : FormBuilder(name) {
    init {
        itemConstructor {
            VersatileItem(ExtendedItemProperties(
                    group = Versatile.MAIN,
                    burnTime = it.burnTime,
                    localizedNameFun = {
                        if (LanguageMap.getInstance().exists(translationKey)) TranslationTextComponent(translationKey)
                        else it.localize()
                    }
            )).setRegistryName(it.registryName)
        }
    }
}

class BlockFormBuilder(name: String) : FormBuilder(name) {
    init {
        itemConstructor {
            VersatileBlockItem(it.block!!, ExtendedItemProperties(
                    group = Versatile.MAIN,
                    burnTime = it.burnTime
            )).setRegistryName(it.registryName)
        }
        blockConstructor {
            VersatileBlock(ExtendedBlockProperties(
                    material = net.minecraft.block.material.Material.IRON,
                    localizedNameFun = {
                        if (LanguageMap.getInstance().exists(translationKey)) TranslationTextComponent(translationKey)
                        else it.localize()
                    }
            ))
        }
        itemModelFunc {
            json {
                "parent" to "versatile:block/materialblocks/" + (if (it.global.singleTextureSet) "" else "${it.mat.textureSet}/") + name
            }
        }
    }
}

class FluidFormBuilder(name: String) : FormBuilder(name) {
    init {
        singleTextureSet()
        blacklistIndices(0)
        bucketVolume(1000)
        itemConstructor { VersatileBucketItem({ it.stillFluid!! }, ExtendedItemProperties(group = Versatile.MAIN, burnTime = it.burnTime)) }
        blockConstructor { VersatileFluidBlock({ it.flowingFluid!! }, ExtendedBlockProperties(net.minecraft.block.material.Material.WATER)) }
        stillFluid { ForgeFlowingFluid.Source(it.fluidProperties!!) }
        flowingFluid { ForgeFlowingFluid.Flowing(it.fluidProperties!!) }
        combinedItemTagNames { emptyList() }
        combinedBlockTagNames { emptyList() }
    }
}