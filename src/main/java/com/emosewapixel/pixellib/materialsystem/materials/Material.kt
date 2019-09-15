package com.emosewapixel.pixellib.materialsystem.materials

import com.emosewapixel.pixellib.materialsystem.element.Elements
import com.emosewapixel.pixellib.materialsystem.lists.Materials
import com.emosewapixel.pixellib.materialsystem.materials.utility.MaterialStack
import com.emosewapixel.pixellib.materialsystem.types.ObjectType
import net.minecraft.item.IArmorMaterial
import net.minecraft.item.IItemTier
import net.minecraft.tags.FluidTags
import net.minecraft.tags.ItemTags
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent
import java.util.*


/*
Materials are objects used for generating items/blocks/fluids based on object types. They have a wide range of customizability.
However, the base Materials aren't meant to be used for generating anything
*/
open class Material constructor(val name: String, val textureType: String, val color: Int, val tier: Int) {
    var itemTier: IItemTier? = null
    var armorMaterial: IArmorMaterial? = null
    val typeBlacklist = ArrayList<ObjectType<*, *>>()
    val materialTags = ArrayList<String>()
    var composition = listOf<MaterialStack>()
    var element = Elements.NULL
    var secondName = name
    var standardBurnTime = 0
    var compoundType = CompoundType.CHEMICAL
    var harvestTier = harvestTier(1.5f * (tier + 1), 1.5f * (tier + 1))

    val localizedName: ITextComponent
        get() = TranslationTextComponent("material.$name")

    val fullComposition: List<MaterialStack>
        get() = if (composition.isEmpty()) listOf(MaterialStack(this)) else composition.flatMap { (material, count) ->
            material.fullComposition
                    .map { (material1, count1) -> MaterialStack(material1, count1 * count) }
        }

    val isPureElement: Boolean
        get() = element !== Elements.NULL

    operator fun invoke(builder: Material.() -> Unit) = builder(this)

    fun build(): Material {
        Materials.add(this)
        return Materials[name]!!
    }

    infix fun hasTag(tag: String) = tag in materialTags

    val hasSecondName
        get() = secondName != name

    fun getItemTag(type: ObjectType<*, *>) = ItemTags.Wrapper(ResourceLocation("forge", type.name + "s/" + name))

    fun getFluidTag(type: ObjectType<*, *>) = FluidTags.Wrapper(ResourceLocation("forge", (if (this !is FluidMaterial) type.name + "_" else "") + name))

    fun getSecondItemTag(type: ObjectType<*, *>) = ItemTags.Wrapper(ResourceLocation("forge", type.name + "s/" + secondName))

    fun getSecondFluidTag(type: ObjectType<*, *>) = FluidTags.Wrapper(ResourceLocation("forge", (if (this !is FluidMaterial) type.name + "_" else "") + secondName))

    fun harvestTier(hardness: Float, resistance: Float) = HarvestTier(hardness, resistance, tier)

    open fun merge(mat: Material) {
        if (mat.itemTier != null)
            itemTier = mat.itemTier
        if (mat.armorMaterial != null)
            armorMaterial = mat.armorMaterial
        typeBlacklist.addAll(mat.typeBlacklist)
        materialTags.addAll(mat.materialTags)
        if (mat.isPureElement)
            element = mat.element
        if (mat.secondName != null)
            secondName = mat.secondName
        if (mat.standardBurnTime != 0)
            standardBurnTime = mat.standardBurnTime
    }
}