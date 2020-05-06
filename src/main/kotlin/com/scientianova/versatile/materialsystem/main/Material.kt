package com.scientianova.versatile.materialsystem.main

import com.scientianova.versatile.Versatile
import com.scientianova.versatile.common.extensions.toResLoc
import com.scientianova.versatile.materialsystem.addition.*
import com.scientianova.versatile.materialsystem.lists.addMaterial
import com.scientianova.versatile.materialsystem.properties.HarvestTier
import com.scientianova.versatile.materialsystem.properties.MatProperty
import net.minecraft.tags.BlockTags
import net.minecraft.tags.FluidTags
import net.minecraft.tags.ItemTags
import net.minecraft.util.text.TranslationTextComponent

class Material {
    val properties = mutableMapOf<MatProperty<out Any?>, Any?>()

    operator fun <T> set(property: MatProperty<T>, value: T) {
        if (property.isValid(value)) properties[property] = value
        else error("Invalid value for property: $property")
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(property: MatProperty<T>) =
            if (property in properties)
                properties[property] as T
            else property.defaultFun(this).also { this[property] = it }

    operator fun contains(property: MatProperty<*>) = property in properties

    internal fun merge(other: Material): Material {
        other.associatedNames = (associatedNames + other.associatedNames).distinct()
        properties += other.properties
        return this
    }

    var associatedNames
        get() = this[ASSOCIATED_NAMES]
        set(value) {
            this[ASSOCIATED_NAMES] = value
        }

    var name
        get() = associatedNames[0]
        set(value) {
            this[ASSOCIATED_NAMES] = listOf(value)
        }

    var composition
        get() = this[COMPOSITION]
        set(value) {
            this[COMPOSITION] = value
        }

    var textureSet
        get() = this[TEXTURE_SET]
        set(value) {
            this[TEXTURE_SET] = value
        }

    var color
        get() = this[COLOR]
        set(value) {
            this[COLOR] = value
        }

    var tier
        get() = this[TIER]
        set(value) {
            this[TIER] = value
        }

    var itemTier
        get() = this[ITEM_TIER]
        set(value) {
            this[ITEM_TIER] = value
        }

    var armorMaterial
        get() = this[ARMOR_MATERIAL]
        set(value) {
            this[ARMOR_MATERIAL] = value
        }

    var element
        get() = this[ELEMENT]
        set(value) {
            this[ELEMENT] = value
        }

    var standardBurnTime
        get() = this[BASE_BURN_TIME]
        set(value) {
            this[BASE_BURN_TIME] = value
        }

    var compoundType
        get() = this[COMPOUND_TYPE]
        set(value) {
            this[COMPOUND_TYPE] = value
        }

    var harvestTier
        get(): HarvestTier {
            val tier = this[HARVEST_TIER]
            if (HARVEST_TIER !in this) this[HARVEST_TIER] = tier
            return tier
        }
        set(value) {
            this[HARVEST_TIER] = value
        }

    var densityMultiplier
        get() = this[DENSITY_MULTIPLIER]
        set(value) {
            this[DENSITY_MULTIPLIER] = value
        }

    var processingMultiplier
        get() = this[PROCESSING_MULTIPLIER]
        set(value) {
            this[PROCESSING_MULTIPLIER] = value
        }

    var refinedMaterial
        get() = this[REFINED_MATERIAL]
        set(value) {
            this[REFINED_MATERIAL] = value
        }

    var liquidTemperature
        get() = this[LIQUID_TEMPERATURE]
        set(value) {
            this[LIQUID_TEMPERATURE] = value
        }

    var gasTemperature
        get() = this[GAS_TEMPERATURE]
        set(value) {
            this[GAS_TEMPERATURE] = value
        }

    var liquidNames
        get() = this[LIQUID_NAMES]
        set(value) {
            this[LIQUID_NAMES] = value
        }

    var gasNames
        get() = this[GAS_NAMES]
        set(value) {
            this[GAS_NAMES] = value
        }

    var gasColor
        get() = this[GAS_COLOR]
        set(value) {
            this[GAS_COLOR] = value
        }

    var unrefinedColor
        get() = this[UNREFINED_COLOR]
        set(value) {
            this[UNREFINED_COLOR] = value
        }

    var alpha
        get() = this[ALPHA]
        set(value) {
            this[ALPHA] = value
        }

    var pH
        get() = this[PH]
        set(value) {
            this[PH] = value
        }

    var blockCompaction
        get() = this[BLOCK_COMPACTION]
        set(value) {
            this[BLOCK_COMPACTION] = value
        }

    var transitionProperties
        get() = this[TRANSITION_PROPERTIES]
        set(value) {
            this[TRANSITION_PROPERTIES] = value
        }

    var hasOre
        get() = this[HAS_ORE]
        set(value) {
            this[HAS_ORE] = value
        }

    var simpleProcessing
        get() = this[SIMPLE_PROCESSING]
        set(value) {
            this[SIMPLE_PROCESSING] = value
        }

    var rodOutputCount
        get() = this[ROD_OUTPUT_COUNT]
        set(value) {
            this[ROD_OUTPUT_COUNT] = value
        }

    var displayType
        get() = this[DISPLAY_TYPE]
        set(value) {
            this[DISPLAY_TYPE] = value
        }

    var hasDust
        get() = this[HAS_DUST]
        set(value) {
            this[HAS_DUST] = value
        }

    var hasIngot
        get() = this[HAS_INGOT]
        set(value) {
            this[HAS_INGOT] = value
        }

    var hasGem
        get() = this[HAS_GEM]
        set(value) {
            this[HAS_GEM] = value
        }

    var malleable
        get() = this[MALLEABLE]
        set(value) {
            this[MALLEABLE] = value
        }

    val localizedName get() = TranslationTextComponent("material.$name")

    val fullComposition: List<MaterialStack>
        get() = if (composition.isEmpty()) listOf(this.toStack()) else composition.flatMap { (material, count) ->
            material.fullComposition.map { (material1, count1) -> material1 * (count1 * count) }
        }

    val isPureElement get() = element !== BaseElements.NULL

    operator fun invoke(builder: Material.() -> Unit) = builder(this)

    override fun toString() = name

    fun register() = addMaterial(this)

    fun getItemTags(type: Form) = associatedNames.map { ItemTags.Wrapper("${type.itemTagNames}/$it".toResLoc()) }

    fun getBlockTags(type: Form) = associatedNames.map { BlockTags.Wrapper("${type.itemTagNames}/$it".toResLoc()) }

    fun getFluidTags(type: Form) = associatedNames.map { FluidTags.Wrapper("${type.itemTagNames}/$it".toResLoc()) }

    @JvmOverloads
    fun getItemTag(form: Form, nameIndex: Int = 0) = getItemTags(form).let {
        it.getOrNull(nameIndex) ?: it.first()
    }

    @JvmOverloads
    fun getBlockTag(form: Form, nameIndex: Int = 0) = getBlockTags(form).let {
        it.getOrNull(nameIndex) ?: it.first()
    }

    @JvmOverloads
    fun getFluidTag(form: Form, nameIndex: Int = 0) = getFluidTags(form).let {
        it.getOrNull(nameIndex) ?: it.first()
    }

    fun harvestTier(hardness: Float, resistance: Float) = HarvestTier(hardness, resistance, tier)

    operator fun times(count: Int) = MaterialStack(this, count)

    @JvmOverloads
    fun toStack(count: Int = 1) = MaterialStack(this, count)
}