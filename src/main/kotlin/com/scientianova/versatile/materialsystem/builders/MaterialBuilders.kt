package com.scientianova.versatile.materialsystem.builders

import com.scientianova.versatile.materialsystem.addition.*
import com.scientianova.versatile.materialsystem.elements.Element
import com.scientianova.versatile.materialsystem.main.Material
import com.scientianova.versatile.materialsystem.main.MaterialStack
import com.scientianova.versatile.materialsystem.properties.*
import net.minecraft.item.IArmorMaterial
import net.minecraft.item.IItemTier

open class MaterialBuilder(vararg names: String) {
    private val result = Material()

    init {
        result.associatedNames = listOf(*names)
    }

    fun <T> property(property: MatProperty<T>, value: T) = this.also { result[property] = value }

    fun composition(value: List<MaterialStack>) = this.also { result.composition = value }

    fun color(value: Int) = property(COLOR, value)

    fun textureSet(value: String) = property(TEXTURE_SET, value)

    fun tier(value: Int) = property(TIER, value)

    fun itemTier(value: IItemTier) = property(ITEM_TIER, value)

    fun armorMaterial(value: IArmorMaterial) = property(ARMOR_MATERIAL, value)

    fun element(value: Element) = property(ELEMENT, value)

    fun standardBurnTime(value: Int) = property(BASE_BURN_TIME, value)

    fun compoundType(value: CompoundType) = property(COMPOUND_TYPE, value)

    fun harvestTier(value: HarvestTier) = property(HARVEST_TIER, value)

    fun densityMultiplier(value: Float) = property(DENSITY_MULTIPLIER, value)

    fun processingMultiplier(value: Int) = property(PROCESSING_MULTIPLIER, value)

    fun refinedMaterial(value: Material) = property(REFINED_MATERIAL, value)

    fun liquidTemperature(value: Int) = property(LIQUID_TEMPERATURE, value)

    fun gasTemperature(value: Int) = property(GAS_TEMPERATURE, value)

    fun liquidNames(vararg names: String) = property(LIQUID_NAMES, listOf(*names))

    fun gasNames(vararg names: String) = property(GAS_NAMES, listOf(*names))

    fun unrefinedColor(value: Int) = property(UNREFINED_COLOR, value)

    fun alpha(value: Int) = property(ALPHA, value)

    fun pH(value: Float) = property(PH, value)

    fun blockCompaction(value: BlockCompaction) = property(BLOCK_COMPACTION, value)

    fun transitionProperties(transitionProperties: TransitionProperties) = property(TRANSITION_PROPERTIES, transitionProperties)

    fun transitionProperties(neededAmount: Int, endMaterial: String) = transitionProperties(TransitionProperties(neededAmount, endMaterial))

    @JvmOverloads
    fun hasOre(value: Boolean = true) = property(HAS_ORE, value)

    fun simpleProcessing(value: Boolean) = property(SIMPLE_PROCESSING, value)

    fun rodOutputCount(value: Int) = property(ROD_OUTPUT_COUNT, value)

    fun displayType(value: DisplayType) = property(DISPLAY_TYPE, value)

    fun hasDust() = property(HAS_ORE, true)

    fun hasGem() = property(HAS_GEM, true)

    fun hasIngot() = property(HAS_INGOT, true)

    fun notMalleable() = property(MALLEABLE, false)

    fun buildAndRegister() = result.register()
}

class DustMaterialBuilder(vararg names: String) : MaterialBuilder(*names) {
    init {
        hasDust()
    }
}

class GemMaterialBuilder(vararg names: String) : MaterialBuilder(*names) {
    init {
        hasDust()
        hasGem()
    }
}

class IngotMaterialBuilder(vararg names: String) : MaterialBuilder(*names) {
    init {
        compoundType(CompoundType.ALLOY)
        hasDust()
        hasIngot()
    }
}

class FluidMaterialBuilder(vararg names: String) : MaterialBuilder(*names) {
    init {
        liquidTemperature(300)
    }
}

class GasMaterialBuilder(vararg names: String) : MaterialBuilder(*names) {
    init {
        gasTemperature(300)
    }
}

class GroupMaterialBuilder(vararg names: String) : MaterialBuilder(*names) {
    init {
        displayType(DisplayType.GROUP)
    }
}