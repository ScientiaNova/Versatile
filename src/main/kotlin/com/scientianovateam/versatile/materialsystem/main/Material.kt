package com.scientianovateam.versatile.materialsystem.main

import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.items.ARMOR_TIERS
import com.scientianovateam.versatile.items.TOOL_TIERS
import com.scientianovateam.versatile.items.tiers.ArmorTier
import com.scientianovateam.versatile.items.tiers.ToolTier
import com.scientianovateam.versatile.materialsystem.addition.MaterialProperties
import com.scientianovateam.versatile.materialsystem.elements.Element
import com.scientianovateam.versatile.materialsystem.lists.Elements
import com.scientianovateam.versatile.materialsystem.lists.MATERIALS
import com.scientianovateam.versatile.materialsystem.properties.BlockCompaction
import com.scientianovateam.versatile.materialsystem.properties.CompoundType
import com.scientianovateam.versatile.materialsystem.properties.IPropertyContainer
import com.scientianovateam.versatile.velisp.evaluated.*
import net.minecraft.tags.BlockTags
import net.minecraft.tags.FluidTags
import net.minecraft.tags.ItemTags
import net.minecraft.util.text.TranslationTextComponent

@Suppress("UNCHECKED_CAST")
class Material(override val properties: Map<String, IEvaluated>) : IPropertyContainer {
    val names: List<String>
        get() = ((properties[MaterialProperties.ASSOCIATED_NAMES] as ListValue).value as List<StringValue>).map(StringValue::value)

    val name get() = names.first()

    val formBlacklist: List<String>
        get() = ((properties[MaterialProperties.FORM_BLACKLIST] as ListValue).value as List<StringValue>).map(StringValue::value)

    val invertedBlacklist: Boolean
        get() = (properties[MaterialProperties.INVERTED_BLACKLIST] as BoolValue).value

    val composition: List<MaterialStack>
        get() = ((properties[MaterialProperties.COMPOSITION] as ListValue).value as List<MaterialStackValue>)
                .map(MaterialStackValue::value)

    val textureSet: String
        get() = (properties[MaterialProperties.TEXTURE_SET] as StringValue).value

    val color: Int
        get() = (properties[MaterialProperties.COLOR] as NumberValue).value.toInt()

    val tier: Int
        get() = (properties[MaterialProperties.TIER] as NumberValue).value.toInt()

    val hardness: Float
        get() = (properties[MaterialProperties.HARDNESS] as NumberValue).value.toFloat()

    val resistance: Float
        get() = (properties[MaterialProperties.RESISTANCE] as NumberValue).value.toFloat()

    val harvestTier: Int
        get() = (properties[MaterialProperties.HARVEST_TIER] as NumberValue).value.toInt()

    val toolTier: ToolTier
        get() = TOOL_TIERS[(properties[MaterialProperties.TOOL_TIER] as StringValue).value]!!

    val armorTier: ArmorTier
        get() = ARMOR_TIERS[(properties[MaterialProperties.ARMOR_TIER] as StringValue).value]!!

    val element: Element
        get() = Elements[(properties[MaterialProperties.ELEMENT] as StringValue).value]

    val burnTime: Int
        get() = (properties[MaterialProperties.BURN_TIME] as NumberValue).value.toInt()

    val compoundType: CompoundType
        get() = CompoundType.valueOf((properties[MaterialProperties.COMPOUND_TYPE] as StringValue).value.toUpperCase())

    val densityMultiplier: Float
        get() = (properties[MaterialProperties.DENSITY_MULTIPLIER] as NumberValue).value.toFloat()

    val processingMultiplier: Int
        get() = (properties[MaterialProperties.PROCESSING_MULTIPLIER] as NumberValue).value.toInt()

    val refinedMaterial: Material?
        get() = when (val optional = properties[MaterialProperties.REFINED_MATERIAL] as OptionalValue) {
            is SomeValue -> (optional.evaluatedValue as MaterialValue).value
            is NullValue -> null
        }

    val unrefinedColor: Int
        get() = (properties[MaterialProperties.UNREFINED_COLOR] as NumberValue).value.toInt()

    val liquidTemperature: Int
        get() = (properties[MaterialProperties.LIQUID_TEMPERATURE] as NumberValue).value.toInt()

    val gasTemperature: Int
        get() = (properties[MaterialProperties.GAS_TEMPERATURE] as NumberValue).value.toInt()

    val liquidName: String
        get() = (properties[MaterialProperties.LIQUID_NAME] as StringValue).value

    val gasName: String
        get() = (properties[MaterialProperties.GAS_NAME] as StringValue).value

    val pH: Float
        get() = (properties[MaterialProperties.PH] as NumberValue).value.toFloat()

    val blockCompaction: BlockCompaction
        get() = BlockCompaction.fromString((properties[MaterialProperties.BLOCK_COMPACTION] as StringValue).value)

    val transitionMaterial: Material?
        get() = MATERIALS[(properties[MaterialProperties.TRANSITION_MATERIAL] as StringValue).value]

    val transitionAmount: Int
        get() = (properties[MaterialProperties.TRANSITION_AMOUNT] as NumberValue).value.toInt()

    val hasOre: Boolean
        get() = (properties[MaterialProperties.HAS_ORE] as BoolValue).value

    val simpleProcessing: Boolean
        get() = (properties[MaterialProperties.SIMPLE_PROCESSING] as BoolValue).value

    val rodOutputCount: Int
        get() = (properties[MaterialProperties.ROD_OUTPUT_COUNT] as NumberValue).value.toInt()

    val group: Boolean
        get() = (properties[MaterialProperties.GROUP] as BoolValue).value

    val hasDust: Boolean
        get() = (properties[MaterialProperties.HAS_DUST] as BoolValue).value

    val hasIngot: Boolean
        get() = (properties[MaterialProperties.HAS_INGOT] as BoolValue).value

    val hasGem: Boolean
        get() = (properties[MaterialProperties.HAS_GEM] as BoolValue).value

    val malleable: Boolean
        get() = (properties[MaterialProperties.MALLEABLE] as BoolValue).value

    val localizedName get() = TranslationTextComponent("material.$name")

    val fullComposition: List<MaterialStack>
        get() = if (composition.isEmpty()) listOf(this.toStack()) else composition.flatMap { (material, count) ->
            material.fullComposition.map { (material1, count1) -> material1 * (count1 * count) }
        }

    operator fun invoke(builder: Material.() -> Unit) = builder(this)

    override fun toString() = name

    fun getItemTags(type: Form) = names.map { ItemTags.Wrapper("${type.itemTag}/$it".toResLoc()) }

    fun getBlockTags(type: Form) = names.map { BlockTags.Wrapper("${type.itemTag}/$it".toResLoc()) }

    fun getFluidTags(type: Form) = names.map { FluidTags.Wrapper("${type.itemTag}/$it".toResLoc()) }

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

    operator fun times(count: Int) = MaterialStack(this, count)

    @JvmOverloads
    fun toStack(count: Int = 1) = MaterialStack(this, count)
}