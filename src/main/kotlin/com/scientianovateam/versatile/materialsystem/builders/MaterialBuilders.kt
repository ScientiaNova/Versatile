package com.scientianovateam.versatile.materialsystem.builders

import com.scientianovateam.versatile.materialsystem.addition.BaseObjTypes
import com.scientianovateam.versatile.materialsystem.addition.BaseTextureTypes
import com.scientianovateam.versatile.materialsystem.addition.MatProperties
import com.scientianovateam.versatile.materialsystem.elements.Element
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.materialsystem.main.MaterialStack
import com.scientianovateam.versatile.materialsystem.main.ObjectType
import com.scientianovateam.versatile.materialsystem.properties.*
import net.minecraft.item.IArmorMaterial
import net.minecraft.item.IItemTier

open class MaterialBuilder(vararg names: String) {
    protected val result = Material(*names)

    fun <T> property(property: MatProperty<T>, value: T) = this.also { result[property] = value }

    fun blacklistTypes(vararg types: ObjectType) = this.also { result.typeBlacklist += types }

    fun invertedBlacklist() = this.also { result.invertedBlacklist = true }

    fun composition(value: List<MaterialStack>) = this.also { result.composition = value }

    fun color(value: Int) = property(MatProperties.COLOR, value)

    fun textureType(value: String) = property(MatProperties.TEXTURE_TYPE, value)

    fun tier(value: Int) = property(MatProperties.TIER, value)

    fun itemTier(value: IItemTier) = property(MatProperties.ITEM_TIER, value)

    fun armorMaterial(value: IArmorMaterial) = property(MatProperties.ARMOR_MATERIAL, value)

    fun element(value: Element) = property(MatProperties.ELEMENT, value)

    fun standardBurnTime(value: Int) = property(MatProperties.BURN_TIME, value)

    fun compoundType(value: CompoundType) = property(MatProperties.COMPOUND_TYPE, value)

    fun harvestTier(value: HarvestTier) = property(MatProperties.HARVEST_TIER, value)

    fun densityMultiplier(value: Float) = property(MatProperties.DENSITY_MULTIPLIER, value)

    fun processingMultiplier(value: Int) = property(MatProperties.PROCESSING_MULTIPLIER, value)

    fun refinedMaterial(value: Material) = property(MatProperties.REFINED_MATERIAL, value)

    fun fluidTemperature(value: Int) = property(MatProperties.FLUID_TEMPERATURE, value)

    fun boilingTemperature(value: Int) = property(MatProperties.BOILING_TEMPERATURE, value)

    fun boilingMaterial(value: Material) = property(MatProperties.BOILING_MATERIAL, value)

    fun unrefinedColor(value: Int) = property(MatProperties.UNREFINED_COLOR, value)

    fun alpha(value: Int) = property(MatProperties.ALPHA, value)

    fun pH(value: Float) = property(MatProperties.PH, value)

    fun blockCompaction(value: BlockCompaction) = property(MatProperties.BLOCK_COMPACTION, value)

    fun transitionProperties(transitionProperties: TransitionProperties) = property(MatProperties.TRANSITION_PROPERTIES, transitionProperties)

    fun transitionProperties(neededAmount: Int, endMaterial: String) = transitionProperties(TransitionProperties(neededAmount, endMaterial))

    @JvmOverloads
    fun hasOre(value: Boolean = true) = property(MatProperties.HAS_ORE, value)

    @JvmOverloads
    fun isGas(value: Boolean = true) = property(MatProperties.IS_GAS, value)

    fun simpleProcessing(value: Boolean) = property(MatProperties.SIMPLE_PROCESSING, value)

    fun rodOutputCount(value: Int) = property(MatProperties.ROD_OUTPUT_COUNT, value)

    fun displayType(value: DisplayType) = property(MatProperties.DISPLAY_TYPE, value)

    @JvmOverloads
    fun hasDust(value: Boolean = true) = property(MatProperties.HAS_ORE, value)

    fun mainItemType(value: ObjectType) = property(MatProperties.MAIN_ITEM_TYPE, value)

    fun buildAndRegister() = result.register()
}

class DustMaterialBuilder(vararg names: String) : MaterialBuilder(*names) {
    init {
        mainItemType(BaseObjTypes.DUST)
        hasDust()
    }
}

class GemMaterialBuilder(vararg names: String) : MaterialBuilder(*names) {
    init {
        mainItemType(BaseObjTypes.GEM)
        hasDust()
    }
}

class IngotMaterialBuilder(vararg names: String) : MaterialBuilder(*names) {
    init {
        compoundType(CompoundType.ALLOY)
        mainItemType(BaseObjTypes.INGOT)
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