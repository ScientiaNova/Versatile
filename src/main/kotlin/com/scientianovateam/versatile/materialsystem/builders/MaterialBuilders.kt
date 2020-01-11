package com.scientianovateam.versatile.materialsystem.builders

import com.scientianovateam.versatile.materialsystem.addition.BaseForms
import com.scientianovateam.versatile.materialsystem.addition.BaseTextureTypes
import com.scientianovateam.versatile.materialsystem.addition.LegacyMatProperties
import com.scientianovateam.versatile.materialsystem.elements.Element
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.materialsystem.main.MaterialStack
import com.scientianovateam.versatile.materialsystem.main.Form
import com.scientianovateam.versatile.materialsystem.properties.*
import net.minecraft.item.IArmorMaterial
import net.minecraft.item.IItemTier

open class MaterialBuilder(vararg names: String) {
    protected val result = Material(*names)

    fun <T> property(property: MatLegacyProperty<T>, value: T) = this.also { result[property] = value }

    fun blacklistTypes(vararg types: Form) = this.also { result.typeBlacklist += types }

    fun invertedBlacklist() = this.also { result.invertedBlacklist = true }

    fun composition(value: List<MaterialStack>) = this.also { result.composition = value }

    fun color(value: Int) = property(LegacyMatProperties.COLOR, value)

    fun textureType(value: String) = property(LegacyMatProperties.TEXTURE_TYPE, value)

    fun tier(value: Int) = property(LegacyMatProperties.TIER, value)

    fun itemTier(value: IItemTier) = property(LegacyMatProperties.ITEM_TIER, value)

    fun armorMaterial(value: IArmorMaterial) = property(LegacyMatProperties.ARMOR_MATERIAL, value)

    //fun element(value: String) = property(LegacyMatProperties.ELEMENT, value)

    fun standardBurnTime(value: Int) = property(LegacyMatProperties.BURN_TIME, value)

    fun compoundType(value: CompoundType) = property(LegacyMatProperties.COMPOUND_TYPE, value)

    fun harvestTier(value: HarvestTier) = property(LegacyMatProperties.HARVEST_TIER, value)

    fun densityMultiplier(value: Float) = property(LegacyMatProperties.DENSITY_MULTIPLIER, value)

    fun processingMultiplier(value: Int) = property(LegacyMatProperties.PROCESSING_MULTIPLIER, value)

    fun refinedMaterial(value: Material) = property(LegacyMatProperties.REFINED_MATERIAL, value)

    fun fluidTemperature(value: Int) = property(LegacyMatProperties.FLUID_TEMPERATURE, value)

    fun boilingTemperature(value: Int) = property(LegacyMatProperties.BOILING_TEMPERATURE, value)

    fun boilingMaterial(value: Material) = property(LegacyMatProperties.BOILING_MATERIAL, value)

    fun unrefinedColor(value: Int) = property(LegacyMatProperties.UNREFINED_COLOR, value)

    fun alpha(value: Int) = property(LegacyMatProperties.ALPHA, value)

    fun pH(value: Float) = property(LegacyMatProperties.PH, value)

    fun blockCompaction(value: BlockCompaction) = property(LegacyMatProperties.BLOCK_COMPACTION, value)

    fun transitionProperties(transitionProperties: TransitionProperties) = property(LegacyMatProperties.TRANSITION_PROPERTIES, transitionProperties)

    fun transitionProperties(neededAmount: Int, endMaterial: String) = transitionProperties(TransitionProperties(neededAmount, endMaterial))

    @JvmOverloads
    fun hasOre(value: Boolean = true) = property(LegacyMatProperties.HAS_ORE, value)

    @JvmOverloads
    fun isGas(value: Boolean = true) = property(LegacyMatProperties.IS_GAS, value)

    fun simpleProcessing(value: Boolean) = property(LegacyMatProperties.SIMPLE_PROCESSING, value)

    fun rodOutputCount(value: Int) = property(LegacyMatProperties.ROD_OUTPUT_COUNT, value)

    fun displayType(value: DisplayType) = property(LegacyMatProperties.DISPLAY_TYPE, value)

    @JvmOverloads
    fun hasDust(value: Boolean = true) = property(LegacyMatProperties.HAS_ORE, value)

    fun mainItemType(value: Form) = property(LegacyMatProperties.MAIN_ITEM_TYPE, value)

    fun buildAndRegister() = result.register()
}

class DustMaterialBuilder(vararg names: String) : MaterialBuilder(*names) {
    init {
        mainItemType(BaseForms.DUST)
        hasDust()
    }
}

class GemMaterialBuilder(vararg names: String) : MaterialBuilder(*names) {
    init {
        mainItemType(BaseForms.GEM)
        hasDust()
    }
}

class IngotMaterialBuilder(vararg names: String) : MaterialBuilder(*names) {
    init {
        compoundType(CompoundType.ALLOY)
        mainItemType(BaseForms.INGOT)
        hasDust()
    }
}

class FluidMaterialBuilder(vararg names: String) : MaterialBuilder(*names) {
    init {
        textureType(BaseTextureTypes.FLUID)
        fluidTemperature(300)
    }
}

class GroupMaterialBuilder(vararg names: String) : MaterialBuilder(*names) {
    init {
        displayType(DisplayType.GROUP)
    }
}