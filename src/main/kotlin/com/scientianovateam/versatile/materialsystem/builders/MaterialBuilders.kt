package com.scientianovateam.versatile.materialsystem.builders

import com.scientianovateam.versatile.materialsystem.addition.BaseTextureTypes
import com.scientianovateam.versatile.materialsystem.addition.MaterialProperties
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.materialsystem.main.MaterialStack
import com.scientianovateam.versatile.materialsystem.properties.BlockCompaction
import com.scientianovateam.versatile.materialsystem.properties.CompoundType
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.evaluated.ListValue
import com.scientianovateam.versatile.velisp.evaluated.SomeValue
import com.scientianovateam.versatile.velisp.expr

open class MaterialBuilder(vararg names: String) {
    private val map = mutableMapOf<String, IEvaluated>()

    init {
        property(MaterialProperties.ASSOCIATED_NAMES, ListValue(names.map(String::expr)))
    }

    fun property(property: String, value: IEvaluated) = this.also { map[property] = value }

    fun composition(value: List<MaterialStack>) = this.also { map[MaterialProperties.COMPOSITION] = value.map(MaterialStack::expr).expr() }

    fun blacklistForms(vararg forms: String) = this.also { map[MaterialProperties.FORM_BLACKLIST] = forms.toList().expr() }

    @JvmOverloads
    fun invertedBlacklist(value: Boolean = true) = this.also { map[MaterialProperties.INVERTED_BLACKLIST] = value.expr() }

    fun textureSet(value: String) = property(MaterialProperties.TEXTURE_SET, value.expr())

    fun color(value: Int) = property(MaterialProperties.COLOR, value.expr())

    fun tier(value: Int) = property(MaterialProperties.TIER, value.expr())

    fun hardness(value: Float) = property(MaterialProperties.HARDNESS, value.expr())

    fun resistance(value: Float) = property(MaterialProperties.RESISTANCE, value.expr())

    fun harvestTier(value: Int) = property(MaterialProperties.HARVEST_TIER, value.expr())

    fun toolTier(value: String) = property(MaterialProperties.TOOL_TIER, SomeValue(value.expr()))

    fun armorTier(value: String) = property(MaterialProperties.ARMOR_TIER, SomeValue(value.expr()))

    fun element(value: String) = property(MaterialProperties.ELEMENT, value.expr())

    fun burnTime(value: Int) = property(MaterialProperties.BURN_TIME, value.expr())

    fun compoundType(value: CompoundType) = property(MaterialProperties.COMPOUND_TYPE, value.name.toLowerCase().expr())

    fun densityMultiplier(value: Float) = property(MaterialProperties.DENSITY_MULTIPLIER, value.expr())

    fun processingMultiplier(value: Int) = property(MaterialProperties.PROCESSING_MULTIPLIER, value.expr())

    fun refinedMaterial(value: Material) = property(MaterialProperties.REFINED_MATERIAL, SomeValue(value.expr()))

    fun unrefinedColor(value: Int) = property(MaterialProperties.UNREFINED_COLOR, value.expr())

    fun liquidTemperature(value: Int) = property(MaterialProperties.LIQUID_TEMPERATURE, value.expr())

    fun gasTemperature(value: Int) = property(MaterialProperties.GAS_TEMPERATURE, value.expr())

    fun liquidName(value: String) = property(MaterialProperties.LIQUID_NAME, value.expr())

    fun gasName(value: String) = property(MaterialProperties.GAS_NAME, value.expr())

    fun gasColor(value: Int) = property(MaterialProperties.GAS_COLOR, value.expr())

    fun pH(value: Float) {
        if (value in 0f..14f) property(MaterialProperties.PH, value.expr())
    }

    fun blockCompaction(value: BlockCompaction) = property(MaterialProperties.BLOCK_COMPACTION, value.serializedName.toLowerCase().expr())

    fun transitionMaterial(value: String) = property(MaterialProperties.TRANSITION_MATERIAL, SomeValue(value.expr()))

    fun transitionAmount(value: Int) = property(MaterialProperties.TRANSITION_AMOUNT, value.expr())

    @JvmOverloads
    fun hasOre(value: Boolean = true) = property(MaterialProperties.HAS_ORE, value.expr())

    fun simpleProcessing(value: Boolean) = property(MaterialProperties.SIMPLE_PROCESSING, value.expr())

    fun rodOutputCount(value: Int) = property(MaterialProperties.ROD_OUTPUT_COUNT, value.expr())

    @JvmOverloads
    fun group(value: Boolean = true) = property(MaterialProperties.GROUP, value.expr())

    @JvmOverloads
    fun hasDust(value: Boolean = true) = property(MaterialProperties.HAS_DUST, value.expr())

    @JvmOverloads
    fun hasIngot(value: Boolean = true) = property(MaterialProperties.HAS_INGOT, value.expr())

    @JvmOverloads
    fun hasGem(value: Boolean = true) = property(MaterialProperties.HAS_GEM, value.expr())

    fun malleable(value: Boolean) = property(MaterialProperties.MALLEABLE, value.expr())

    fun build() = Material(map)
}

fun material(vararg names: String, builder: MaterialBuilder.() -> Unit) = MaterialBuilder(*names).apply(builder).build()

open class DustMaterialBuilder(vararg names: String) : MaterialBuilder(*names) {
    init {
        hasDust()
    }
}

fun dustMaterial(vararg names: String, builder: MaterialBuilder.() -> Unit) = DustMaterialBuilder(*names).apply(builder).build()

open class GemMaterialBuilder(vararg names: String) : MaterialBuilder(*names) {
    init {
        hasGem()
        hasDust()
    }
}

fun gemMaterial(vararg names: String, builder: GemMaterialBuilder.() -> Unit) = GemMaterialBuilder(*names).apply(builder).build()

open class IngotMaterialBuilder(vararg names: String) : MaterialBuilder(*names) {
    init {
        compoundType(CompoundType.ALLOY)
        hasIngot()
        hasDust()
    }
}

fun ingotMaterial(vararg names: String, builder: IngotMaterialBuilder.() -> Unit) = IngotMaterialBuilder(*names).apply(builder).build()

open class LiquidMaterialBuilder(vararg names: String) : MaterialBuilder(*names) {
    init {
        textureSet(BaseTextureTypes.FLUID)
        liquidTemperature(300)
    }
}

fun liquidMaterial(vararg names: String, builder: LiquidMaterialBuilder.() -> Unit) = LiquidMaterialBuilder(*names).apply(builder).build()

open class GasMaterialBuilder(vararg names: String) : MaterialBuilder(*names) {
    init {
        textureSet(BaseTextureTypes.FLUID)
        gasTemperature(300)
    }
}

fun gasMaterial(vararg names: String, builder: GasMaterialBuilder.() -> Unit) = GasMaterialBuilder(*names).apply(builder).build()

var MaterialBuilder.composition: List<MaterialStack>?
    get() = null
    set(value) {
        if (value != null) composition(value)
    }

var MaterialBuilder.formBlackList: Array<String>?
    get() = null
    set(value) {
        if (value != null) blacklistForms(*value)
    }

var MaterialBuilder.invertedBlacklist: Boolean?
    get() = null
    set(value) {
        invertedBlacklist(value == true)
    }

var MaterialBuilder.textureSet: String?
    get() = null
    set(value) {
        if (value != null)
            textureSet(value)
    }

var MaterialBuilder.color: Number?
    get() = null
    set(value) {
        if (value != null)
            color(value.toInt())
    }

var MaterialBuilder.tier: Int?
    get() = null
    set(value) {
        if (value != null)
            tier(value)
    }

var MaterialBuilder.hardness: Float?
    get() = null
    set(value) {
        if (value != null)
            hardness(value)
    }

var MaterialBuilder.resistance: Float?
    get() = null
    set(value) {
        if (value != null)
            resistance(value)
    }

var MaterialBuilder.harvestTier: Int?
    get() = null
    set(value) {
        if (value != null)
            harvestTier(value)
    }

var MaterialBuilder.toolTier: String?
    get() = null
    set(value) {
        if (value != null)
            toolTier(value)
    }

var MaterialBuilder.armorTier: String?
    get() = null
    set(value) {
        if (value != null)
            armorTier(value)
    }

var MaterialBuilder.element: String?
    get() = null
    set(value) {
        if (value != null)
            element(value)
    }

var MaterialBuilder.burnTime: Int?
    get() = null
    set(value) {
        if (value != null)
            burnTime(value)
    }

var MaterialBuilder.compoundType: CompoundType?
    get() = null
    set(value) {
        if (value != null)
            compoundType(value)
    }

var MaterialBuilder.densityMultiplier: Float?
    get() = null
    set(value) {
        if (value != null)
            densityMultiplier(value)
    }

var MaterialBuilder.processingMultiplier: Int?
    get() = null
    set(value) {
        if (value != null)
            processingMultiplier(value)
    }

var MaterialBuilder.refinedMayerial: Material?
    get() = null
    set(value) {
        if (value != null)
            refinedMaterial(value)
    }

var MaterialBuilder.unrefinedColor: Number?
    get() = null
    set(value) {
        if (value != null)
            unrefinedColor(value.toInt())
    }

var MaterialBuilder.liquidTemperature: Int?
    get() = null
    set(value) {
        if (value != null)
            liquidTemperature(value)
    }

var MaterialBuilder.gasTemperature: Int?
    get() = null
    set(value) {
        if (value != null)
            gasTemperature(value)
    }

var MaterialBuilder.liquidName: String?
    get() = null
    set(value) {
        if (value != null)
            liquidName(value)
    }

var MaterialBuilder.gasName: String?
    get() = null
    set(value) {
        if (value != null)
            gasName(value)
    }

var MaterialBuilder.gasColor: Number?
    get() = null
    set(value) {
        if (value != null)
            gasColor(value.toInt())
    }

var MaterialBuilder.pH: Float?
    get() = null
    set(value) {
        if (value != null)
            pH(value)
    }

var MaterialBuilder.blockCompaction: BlockCompaction?
    get() = null
    set(value) {
        if (value != null)
            blockCompaction(value)
    }

var MaterialBuilder.transitionMaterial: String?
    get() = null
    set(value) {
        if (value != null)
            transitionMaterial(value)
    }

var MaterialBuilder.transitionAmount: Int?
    get() = null
    set(value) {
        if (value != null)
            transitionAmount(value)
    }

var MaterialBuilder.hasOre: Boolean?
    get() = null
    set(value) {
        hasOre(value == true)
    }

var MaterialBuilder.simpleProcessing: Boolean?
    get() = null
    set(value) {
        simpleProcessing(value == true)
    }

var MaterialBuilder.rodOutputCount: Int?
    get() = null
    set(value) {
        if (value != null)
            rodOutputCount(value)
    }

var MaterialBuilder.group: Boolean?
    get() = null
    set(value) {
        group(value == true)
    }

var MaterialBuilder.hasDust: Boolean?
    get() = null
    set(value) {
        hasDust(value == true)
    }

var MaterialBuilder.hasIngot: Boolean?
    get() = null
    set(value) {
        hasIngot(value == true)
    }

var MaterialBuilder.hasGem: Boolean?
    get() = null
    set(value) {
        hasGem(value == true)
    }

var MaterialBuilder.malleable: Boolean?
    get() = null
    set(value) {
        malleable(value == true)
    }