package com.scientianovateam.versatile.materialsystem.main

import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.materialsystem.addition.BaseElements
import com.scientianovateam.versatile.materialsystem.addition.BaseForms
import com.scientianovateam.versatile.materialsystem.addition.LegacyMatProperties
import com.scientianovateam.versatile.materialsystem.addition.MaterialProperties
import com.scientianovateam.versatile.materialsystem.lists.Materials
import com.scientianovateam.versatile.materialsystem.properties.HarvestTier
import com.scientianovateam.versatile.materialsystem.properties.IPropertyContainer
import com.scientianovateam.versatile.materialsystem.properties.MatLegacyProperty
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.evaluated.NumberValue
import net.minecraft.tags.BlockTags
import net.minecraft.tags.FluidTags
import net.minecraft.tags.ItemTags
import net.minecraft.util.text.TranslationTextComponent
import java.util.*

class Material(override val properties: Map<String, IEvaluated>) : IPropertyContainer {
    val names = mutableListOf<String>()
    val name get() = names.first()

    constructor(vararg name: String) : this(mutableMapOf()) {
        names.addAll(name) // TODO Legacy constructor
    }

    val legacyProperties = mutableMapOf<MatLegacyProperty<out Any?>, Any?>()

    operator fun <T> set(property: MatLegacyProperty<T>, value: T) {
        if (property.isValid(value)) legacyProperties[property] = value
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(property: MatLegacyProperty<T>) = legacyProperties[property] as? T ?: property.default(this)

    operator fun contains(property: MatLegacyProperty<*>) = property in legacyProperties

    val color: Int
        get() = (properties[MaterialProperties.COLOR] as NumberValue).value.toInt()

    val typeBlacklist = ArrayList<Form>()

    var invertedBlacklist = false

    var composition = listOf<MaterialStack>()

    var textureType
        get() = this[LegacyMatProperties.TEXTURE_TYPE]
        set(value) {
            this[LegacyMatProperties.TEXTURE_TYPE] = value
        }

    var legacyColor
        get() = this[LegacyMatProperties.COLOR]
        set(value) {
            this[LegacyMatProperties.COLOR] = value
        }

    var tier
        get() = this[LegacyMatProperties.TIER]
        set(value) {
            this[LegacyMatProperties.TIER] = value
        }

    var itemTier
        get() = this[LegacyMatProperties.ITEM_TIER]
        set(value) {
            this[LegacyMatProperties.ITEM_TIER] = value
        }

    var armorMaterial
        get() = this[LegacyMatProperties.ARMOR_MATERIAL]
        set(value) {
            this[LegacyMatProperties.ARMOR_MATERIAL] = value
        }

    var element
        get() = this[LegacyMatProperties.ELEMENT]
        set(value) {
            this[LegacyMatProperties.ELEMENT] = value
        }

    var standardBurnTime
        get() = this[LegacyMatProperties.BURN_TIME]
        set(value) {
            this[LegacyMatProperties.BURN_TIME] = value
        }

    var compoundType
        get() = this[LegacyMatProperties.COMPOUND_TYPE]
        set(value) {
            this[LegacyMatProperties.COMPOUND_TYPE] = value
        }

    var harvestTier
        get(): HarvestTier {
            val tier = this[LegacyMatProperties.HARVEST_TIER]
            if (LegacyMatProperties.HARVEST_TIER !in this) this[LegacyMatProperties.HARVEST_TIER] = tier
            return tier
        }
        set(value) {
            this[LegacyMatProperties.HARVEST_TIER] = value
        }

    var densityMultiplier
        get() = this[LegacyMatProperties.DENSITY_MULTIPLIER]
        set(value) {
            this[LegacyMatProperties.DENSITY_MULTIPLIER] = value
        }

    var processingMultiplier
        get() = this[LegacyMatProperties.PROCESSING_MULTIPLIER]
        set(value) {
            this[LegacyMatProperties.PROCESSING_MULTIPLIER] = value
        }

    var refinedMaterial
        get() = this[LegacyMatProperties.REFINED_MATERIAL]
        set(value) {
            this[LegacyMatProperties.REFINED_MATERIAL] = value
        }

    var fluidTemperature
        get() = this[LegacyMatProperties.FLUID_TEMPERATURE]
        set(value) {
            this[LegacyMatProperties.FLUID_TEMPERATURE] = value
        }

    var boilingTemperature
        get() = this[LegacyMatProperties.BOILING_TEMPERATURE]
        set(value) {
            this[LegacyMatProperties.BOILING_TEMPERATURE] = value
        }

    var boilingMaterial
        get() = this[LegacyMatProperties.BOILING_MATERIAL]
        set(value) {
            this[LegacyMatProperties.BOILING_MATERIAL] = value
        }

    var unrefinedColor
        get() = this[LegacyMatProperties.UNREFINED_COLOR]
        set(value) {
            this[LegacyMatProperties.UNREFINED_COLOR] = value
        }

    var alpha
        get() = this[LegacyMatProperties.ALPHA]
        set(value) {
            this[LegacyMatProperties.ALPHA] = value
        }

    var pH
        get() = this[LegacyMatProperties.PH]
        set(value) {
            this[LegacyMatProperties.PH] = value
        }

    var blockCompaction
        get() = this[LegacyMatProperties.BLOCK_COMPACTION]
        set(value) {
            this[LegacyMatProperties.BLOCK_COMPACTION] = value
        }

    var transitionProperties
        get() = this[LegacyMatProperties.TRANSITION_PROPERTIES]
        set(value) {
            this[LegacyMatProperties.TRANSITION_PROPERTIES] = value
        }

    var hasOre
        get() = this[LegacyMatProperties.HAS_ORE]
        set(value) {
            this[LegacyMatProperties.HAS_ORE] = value
        }

    var isGas
        get() = this[LegacyMatProperties.IS_GAS]
        set(value) {
            this[LegacyMatProperties.IS_GAS] = value
        }

    var simpleProcessing
        get() = this[LegacyMatProperties.SIMPLE_PROCESSING]
        set(value) {
            this[LegacyMatProperties.SIMPLE_PROCESSING] = value
        }

    var rodOutputCount
        get() = this[LegacyMatProperties.ROD_OUTPUT_COUNT]
        set(value) {
            this[LegacyMatProperties.ROD_OUTPUT_COUNT] = value
        }

    var displayType
        get() = this[LegacyMatProperties.DISPLAY_TYPE]
        set(value) {
            this[LegacyMatProperties.DISPLAY_TYPE] = value
        }

    var hasDust
        get() = this[LegacyMatProperties.HAS_DUST]
        set(value) {
            this[LegacyMatProperties.HAS_DUST] = value
        }

    var mainItemType
        get() = this[LegacyMatProperties.MAIN_ITEM_TYPE]
        set(value) {
            this[LegacyMatProperties.MAIN_ITEM_TYPE] = value
        }

    val localizedName get() = TranslationTextComponent("material.$name")

    val fullComposition: List<MaterialStack>
        get() = if (composition.isEmpty()) listOf(this.toStack()) else composition.flatMap { (material, count) ->
            material.fullComposition.map { (material1, count1) -> material1 * (count1 * count) }
        }

    val isPureElement get() = element !== BaseElements.NULL

    val isItemMaterial get() = mainItemType != null

    val isIngotMaterial: Boolean get() = mainItemType == BaseForms.INGOT

    val isGemMaterial: Boolean get() = mainItemType == BaseForms.GEM

    val isFluidMaterial get() = mainItemType == null && fluidTemperature > 0

    operator fun invoke(builder: Material.() -> Unit) = builder(this)

    override fun toString() = name

    fun register(): Material {
        Materials.add(this)
        return Materials[name]!!
    }

    fun merge(mat: Material): Material {
        names union mat.names
        mat.legacyProperties.forEach { (key, value) ->
            key.merge(legacyProperties[key], value)?.let { legacyProperties[key] = it }
        }
        typeBlacklist.addAll(mat.typeBlacklist)
        return this
    }

    fun getItemTags(type: Form) = names.map { ItemTags.Wrapper("${type.itemTagName}/$it".toResLoc()) }

    fun getBlockTags(type: Form) = names.map { BlockTags.Wrapper("${type.itemTagName}/$it".toResLoc()) }

    fun getFluidTags(type: Form) = names.map { FluidTags.Wrapper("${type.itemTagName}/$it".toResLoc()) }

    @JvmOverloads
    fun getItemTag(type: Form, nameIndex: Int = 0) = getItemTags(type).let {
        it.getOrNull(nameIndex) ?: it.first()
    }

    @JvmOverloads
    fun getBlockTag(type: Form, nameIndex: Int = 0) = getBlockTags(type).let {
        it.getOrNull(nameIndex) ?: it.first()
    }

    @JvmOverloads
    fun getFluidTag(type: Form, nameIndex: Int = 0) = getFluidTags(type).let {
        it.getOrNull(nameIndex) ?: it.first()
    }

    fun harvestTier(hardness: Float, resistance: Float) = HarvestTier(hardness, resistance, tier)

    operator fun times(count: Int) = MaterialStack(this, count)

    @JvmOverloads
    fun toStack(count: Int = 1) = MaterialStack(this, count)
}