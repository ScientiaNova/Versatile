package com.emosewapixel.pixellib.materialsystem.materials

import com.blamejared.crafttweaker.api.annotations.ZenRegister
import com.emosewapixel.pixellib.materialsystem.elements.BaseElements
import com.emosewapixel.pixellib.materialsystem.lists.Materials
import com.emosewapixel.pixellib.materialsystem.materials.utility.MaterialStack
import com.emosewapixel.pixellib.materialsystem.properties.MatProperties
import com.emosewapixel.pixellib.materialsystem.properties.MatProperty
import com.emosewapixel.pixellib.materialsystem.types.ObjectType
import net.minecraft.tags.FluidTags
import net.minecraft.tags.ItemTags
import net.minecraft.util.text.TranslationTextComponent
import org.openzen.zencode.java.ZenCodeType
import java.util.*

/*
Materials are objects used for generating items/blocks/fluids based on object types. They have a wide range of customizability.
However, the base Materials aren't meant to be used for generating anything
*/
@ZenRegister
@ZenCodeType.Name("pixellib.materialsystem.materials.Material")
open class Material @ZenCodeType.Constructor constructor(@ZenCodeType.Field val name: String) {

    private val properties = mutableMapOf<MatProperty<*>, Any?>()

    operator fun <T> set(property: MatProperty<T>, value: T) {
        properties[property] = value
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(property: MatProperty<T>) = properties[property] as? T ?: property.default(this)

    operator fun contains(property: MatProperty<*>) = property in properties

    val typeBlacklist = ArrayList<ObjectType<*, *>>()

    val invertedBlacklist = false

    var composition = listOf<MaterialStack>()

    var textureType
        get() = this[MatProperties.TEXTURE_TYPE]
        set(value) {
            this[MatProperties.TEXTURE_TYPE] = value
        }

    var color
        get() = this[MatProperties.COLOR]
        set(value) {
            this[MatProperties.COLOR] = value
        }

    var tier
        get() = this[MatProperties.TIER]
        set(value) {
            this[MatProperties.TIER] = value
        }

    var itemTier
        get() = this[MatProperties.ITEM_TIER]
        set(value) {
            this[MatProperties.ITEM_TIER] = value
        }

    var armorMaterial
        get() = this[MatProperties.ARMOR_MATERIAL]
        set(value) {
            this[MatProperties.ARMOR_MATERIAL] = value
        }

    var element
        get() = this[MatProperties.ELEMENT]
        set(value) {
            this[MatProperties.ELEMENT] = value
        }

    var secondName
        get() = this[MatProperties.SECOND_NAME]
        set(value) {
            this[MatProperties.SECOND_NAME] = value
        }

    var standardBurnTime
        get() = this[MatProperties.BURN_TIME]
        set(value) {
            this[MatProperties.BURN_TIME] = value
        }

    var compoundType
        get() = this[MatProperties.COMPOUND_TYPE]
        set(value) {
            this[MatProperties.COMPOUND_TYPE] = value
        }

    var harvestTier
        get(): HarvestTier {
            val tier = this[MatProperties.HARVEST_TIER]
            if (MatProperties.HARVEST_TIER !in this) this[MatProperties.HARVEST_TIER] = tier
            return tier
        }
        set(value) {
            this[MatProperties.HARVEST_TIER] = value
        }

    var densityMultiplier
        get() = this[MatProperties.DENSITY_MULTIPLIER]
        set(value) {
            this[MatProperties.DENSITY_MULTIPLIER] = value
        }

    var processingMultiplier
        get() = this[MatProperties.PROCESSING_MULTIPLIER]
        set(value) {
            this[MatProperties.PROCESSING_MULTIPLIER] = value
        }

    var refinedMaterial
        get() = this[MatProperties.REFINED_MATERIAL]
        set(value) {
            this[MatProperties.REFINED_MATERIAL] = value
        }

    var fluidTemperature
        get() = this[MatProperties.FLUID_TEMPERATURE]
        set(value) {
            this[MatProperties.FLUID_TEMPERATURE] = value
        }

    var boilingTemperature
        get() = this[MatProperties.BOILING_TEMPERATURE]
        set(value) {
            this[MatProperties.BOILING_TEMPERATURE] = value
        }

    var boilingMaterial
        get() = this[MatProperties.BOILING_MATERIAL]
        set(value) {
            this[MatProperties.BOILING_MATERIAL] = value
        }

    var unrefinedColor
        get() = this[MatProperties.UNREFINED_COLOR]
        set(value) {
            this[MatProperties.UNREFINED_COLOR] = value
        }

    var alpha
        get() = this[MatProperties.ALPHA]
        set(value) {
            this[MatProperties.ALPHA] = value
        }

    var pH
        get() = this[MatProperties.PH]
        set(value) {
            this[MatProperties.PH] = value
        }

    var blockCompaction
        get() = this[MatProperties.BLOCK_COMPACTION]
        set(value) {
            this[MatProperties.BLOCK_COMPACTION] = value
        }

    var transitionProperties
        get() = this[MatProperties.TRANSITION_PROPERTIES]
        set(value) {
            this[MatProperties.TRANSITION_PROPERTIES] = value
        }

    var hasOre
        get() = this[MatProperties.HAS_ORE]
        set(value) {
            this[MatProperties.HAS_ORE] = value
        }

    var isGas
        get() = this[MatProperties.IS_GAS]
        set(value) {
            this[MatProperties.IS_GAS] = value
        }

    var simpleProcessing
        get() = this[MatProperties.SIMPLE_PROCESSING]
        set(value) {
            this[MatProperties.SIMPLE_PROCESSING] = value
        }

    var rodOutputCount
        get() = this[MatProperties.ROD_OUTPUT_COUNT]
        set(value) {
            this[MatProperties.ROD_OUTPUT_COUNT] = value
        }

    var isDustMaterial
        get() = this[MatProperties.IS_DUST_MATERIAL]
        set(value) {
            this[MatProperties.IS_DUST_MATERIAL] = value
        }

    var isIngotMaterial
        get() = this[MatProperties.IS_INGOT_MATERIAL]
        set(value) {
            this[MatProperties.IS_INGOT_MATERIAL] = value
        }

    var isGemMaterial
        get() = this[MatProperties.IS_GEM_MATERIAL]
        set(value) {
            this[MatProperties.IS_GEM_MATERIAL] = value
        }

    var isFluidMaterial
        get() = this[MatProperties.IS_FLUID_MATERIAL]
        set(value) {
            this[MatProperties.IS_FLUID_MATERIAL] = value
        }

    var isGroupMaterial
        get() = this[MatProperties.IS_GROUP_MATERIAL]
        set(value) {
            this[MatProperties.IS_GROUP_MATERIAL] = value
        }

    val localizedName get() = TranslationTextComponent("material.$name")

    val fullComposition: List<MaterialStack>
        get() = if (composition.isEmpty()) listOf(this.toStack()) else composition.flatMap { (material, count) ->
            material.fullComposition.map { (material1, count1) -> material1 * (count1 * count) }
        }

    val hasSecondName get() = secondName != name

    val isPureElement get() = element !== BaseElements.NULL

    operator fun invoke(builder: Material.() -> Unit) = builder(this)

    @ZenCodeType.Method
    override fun toString() = name

    @ZenCodeType.Method
    fun build(): Material {
        Materials.add(this)
        return Materials[name]!!
    }

    fun merge(mat: Material) {
        mat.properties.forEach { (key, value) ->
            properties[key] = if (key !in properties) value else properties[key] ?: value
        }
        typeBlacklist.addAll(mat.typeBlacklist)
    }

    @ZenCodeType.Getter
    fun getItemTag(type: ObjectType<*, *>) = ItemTags.Wrapper(type.buildTagName(name))

    @ZenCodeType.Getter
    fun getFluidTag(type: ObjectType<*, *>) = FluidTags.Wrapper(type.buildTagName(name))

    @ZenCodeType.Getter
    fun getSecondItemTag(type: ObjectType<*, *>) = ItemTags.Wrapper(type.buildTagName(name))

    @ZenCodeType.Getter
    fun getSecondFluidTag(type: ObjectType<*, *>) = FluidTags.Wrapper(type.buildTagName(name))

    @ZenCodeType.Method
    fun harvestTier(hardness: Float, resistance: Float) = HarvestTier(hardness, resistance, tier)

    @ZenCodeType.Operator(ZenCodeType.OperatorType.MUL)
    operator fun times(count: Int) = MaterialStack(this, count)

    @JvmOverloads
    fun toStack(count: Int = 1) = MaterialStack(this, count)
}