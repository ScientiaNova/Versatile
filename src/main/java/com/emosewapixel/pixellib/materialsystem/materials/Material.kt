package com.emosewapixel.pixellib.materialsystem.materials

import com.blamejared.crafttweaker.api.annotations.ZenRegister
import com.emosewapixel.pixellib.materialsystem.elements.BaseElements
import com.emosewapixel.pixellib.materialsystem.lists.Materials
import com.emosewapixel.pixellib.materialsystem.materials.utility.MaterialStack
import com.emosewapixel.pixellib.materialsystem.types.ObjectType
import net.minecraft.item.IArmorMaterial
import net.minecraft.item.IItemTier
import net.minecraft.tags.FluidTags
import net.minecraft.tags.ItemTags
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent
import org.openzen.zencode.java.ZenCodeType
import java.util.*

/*
Materials are objects used for generating items/blocks/fluids based on object types. They have a wide range of customizability.
However, the base Materials aren't meant to be used for generating anything
*/
@ZenRegister
@ZenCodeType.Name("pixellib.materialsystem.materials.Material")
open class Material @ZenCodeType.Constructor
constructor(@ZenCodeType.Field val name: String,
            @ZenCodeType.Field val textureType: String,
            @ZenCodeType.Field val color: Int,
            @ZenCodeType.Field val tier: Int) {

    @ZenCodeType.Field
    var itemTier: IItemTier? = null

    @ZenCodeType.Field
    var armorMaterial: IArmorMaterial? = null

    val typeBlacklist = ArrayList<ObjectType<*, *>>()
        @ZenCodeType.Getter get

    val invertedBlacklist = false
        @ZenCodeType.Getter get

    val materialTags = ArrayList<String>()
        @ZenCodeType.Getter get

    @ZenCodeType.Field
    var composition = listOf<MaterialStack>()

    @ZenCodeType.Field
    var element = BaseElements.NULL

    @ZenCodeType.Field
    var secondName = name

    @ZenCodeType.Field
    var standardBurnTime = 0

    @ZenCodeType.Field
    var compoundType = CompoundType.CHEMICAL

    @ZenCodeType.Field
    var harvestTier = harvestTier(1.5f * (tier + 1), 1.5f * (tier + 1))

    val extraProperties = mutableMapOf<String, Any>()
        @ZenCodeType.Getter get

    @ZenCodeType.Field
    var densityMultiplier = 1f

    val localizedName: ITextComponent
        get() = TranslationTextComponent("material.$name")

    val fullComposition: List<MaterialStack>
        get() = if (composition.isEmpty()) listOf(this.toStack()) else composition.flatMap { (material, count) ->
            material.fullComposition.map { (material1, count1) -> material1 * (count1 * count) }
        }

    val isPureElement: Boolean
        get() = element !== BaseElements.NULL

    operator fun invoke(builder: Material.() -> Unit) = builder(this)

    @ZenCodeType.Method
    infix fun hasTag(tag: String) = tag in materialTags

    @ZenCodeType.Method
    override fun toString() = name

    open fun merge(mat: Material) {
        if (mat.itemTier != null)
            itemTier = mat.itemTier
        if (mat.armorMaterial != null)
            armorMaterial = mat.armorMaterial
        if (mat.isPureElement)
            element = mat.element
        if (mat.hasSecondName)
            secondName = mat.secondName
        if (mat.standardBurnTime != 0)
            standardBurnTime = mat.standardBurnTime
        typeBlacklist.addAll(mat.typeBlacklist)
        materialTags.addAll(mat.materialTags)
        extraProperties.putAll(mat.extraProperties)
    }

    @ZenCodeType.Method
    fun build(): Material {
        Materials.add(this)
        return Materials[name]!!
    }

    val hasSecondName
        get() = secondName != name

    @ZenCodeType.Getter
    fun getTag(type: ObjectType<*, *>) = ItemTags.Wrapper(type.buildTagName(name))

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