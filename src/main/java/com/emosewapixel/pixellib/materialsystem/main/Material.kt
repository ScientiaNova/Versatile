package com.emosewapixel.pixellib.materialsystem.main

import com.blamejared.crafttweaker.api.annotations.ZenRegister
import com.emosewapixel.pixellib.extensions.toResLoc
import com.emosewapixel.pixellib.materialsystem.addition.BaseObjTypes
import com.emosewapixel.pixellib.materialsystem.addition.BaseElements
import com.emosewapixel.pixellib.materialsystem.lists.Materials
import com.emosewapixel.pixellib.materialsystem.properties.HarvestTier
import com.emosewapixel.pixellib.materialsystem.properties.MatProperties
import com.emosewapixel.pixellib.materialsystem.properties.MatProperty
import net.minecraft.tags.BlockTags
import net.minecraft.tags.FluidTags
import net.minecraft.tags.ItemTags
import net.minecraft.util.text.TranslationTextComponent
import org.openzen.zencode.java.ZenCodeType
import java.util.*

@ZenRegister
@ZenCodeType.Name("pixellib.materialsystem.materials.Material")
open class Material @ZenCodeType.Constructor constructor(@ZenCodeType.Field val name: String) {
    val properties = mutableMapOf<MatProperty<out Any?>, Any?>()

    operator fun <T> set(property: MatProperty<T>, value: T) {
        if (property.isValid(value)) properties[property] = value
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(property: MatProperty<T>) = properties[property] as? T ?: property.default(this)

    operator fun contains(property: MatProperty<*>) = property in properties

    val typeBlacklist = ArrayList<ObjectType>()

    var invertedBlacklist = false

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

    var displayType
        get() = this[MatProperties.DISPLAY_TYPE]
        set(value) {
            this[MatProperties.DISPLAY_TYPE] = value
        }

    var hasDust
        get() = this[MatProperties.HAS_DUST]
        set(value) {
            this[MatProperties.HAS_DUST] = value
        }

    var mainItemType
        get() = this[MatProperties.MAIN_ITEM_TYPE]
        set(value) {
            this[MatProperties.MAIN_ITEM_TYPE] = value
        }

    val localizedName get() = TranslationTextComponent("material.$name")

    val fullComposition: List<MaterialStack>
        get() = if (composition.isEmpty()) listOf(this.toStack()) else composition.flatMap { (material, count) ->
            material.fullComposition.map { (material1, count1) -> material1 * (count1 * count) }
        }

    val hasSecondName get() = secondName != name

    val isPureElement get() = element !== BaseElements.NULL

    val isItemMaterial get() = mainItemType != null

    val isIngotMaterial: Boolean get() = mainItemType == BaseObjTypes.INGOT

    val isGemMaterial: Boolean get() = mainItemType == BaseObjTypes.GEM

    val isFluidMaterial get() = mainItemType == null && fluidTemperature > 0

    operator fun invoke(builder: Material.() -> Unit) = builder(this)

    @ZenCodeType.Method
    override fun toString() = name

    @ZenCodeType.Method
    fun register(): Material {
        Materials.add(this)
        return Materials[name]!!
    }

    fun merge(mat: Material) {
        mat.properties.forEach { (key, value) ->
            key.merge(properties[key], value)?.let { properties[key] = it }
        }
        typeBlacklist.addAll(mat.typeBlacklist)
    }

    @ZenCodeType.Getter
    fun getItemTag(type: ObjectType) = ItemTags.Wrapper("${type.itemTagName}/$name".toResLoc())

    @ZenCodeType.Getter
    fun getBlockTag(type: ObjectType) = BlockTags.Wrapper("${type.blockTagName}/$name".toResLoc())

    @ZenCodeType.Getter
    fun getFluidTag(type: ObjectType) = FluidTags.Wrapper("${type.fluidTagName}$name".toResLoc())

    @ZenCodeType.Getter
    fun getSecondItemTag(type: ObjectType) = ItemTags.Wrapper("${type.itemTagName}/$secondName".toResLoc())

    @ZenCodeType.Getter
    fun getSecondBlockTag(type: ObjectType) = BlockTags.Wrapper("${type.blockTagName}/$secondName".toResLoc())

    @ZenCodeType.Getter
    fun getSecondFluidTag(type: ObjectType) = FluidTags.Wrapper("${type.fluidTagName}/$secondName".toResLoc())

    @ZenCodeType.Method
    fun harvestTier(hardness: Float, resistance: Float) = HarvestTier(hardness, resistance, tier)

    @ZenCodeType.Operator(ZenCodeType.OperatorType.MUL)
    operator fun times(count: Int) = MaterialStack(this, count)

    @JvmOverloads
    fun toStack(count: Int = 1) = MaterialStack(this, count)
}