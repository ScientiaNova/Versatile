package com.EmosewaPixel.pixellib.materialsystem.materials

import com.EmosewaPixel.pixellib.materialsystem.element.Elements
import com.EmosewaPixel.pixellib.materialsystem.lists.Materials
import com.EmosewaPixel.pixellib.materialsystem.materials.utility.MaterialStack
import com.EmosewaPixel.pixellib.materialsystem.types.ObjectType
import com.EmosewaPixel.pixellib.materialsystem.types.TextureType
import net.minecraft.item.IArmorMaterial
import net.minecraft.item.IItemTier
import net.minecraft.tags.ItemTags
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent
import java.util.*


/*
Materials are objects used for generating items/blocks/fluids based on object types. They have a wide range of customizability.
However, the base Materials aren't meant to be used for generating anything
*/
open class Material constructor(val name: String, val textureType: TextureType?, val color: Int, val tier: Int) {
    var itemTier: IItemTier? = null
    var armorMaterial: IArmorMaterial? = null
    val typeBlacklist = ArrayList<ObjectType>()
    val materialTags = ArrayList<String>()
    var composition = mutableListOf<MaterialStack>()
    var element = Elements.NULL
    var secondName: String? = null
    var standardBurnTime = 0
    var compoundType = CompoundType.CHEMICAL

    val translationKey: ITextComponent
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

    fun hasTag(tag: String) = tag in materialTags

    fun hasSecondName() = secondName != name

    fun getTag(type: ObjectType) = ItemTags.Wrapper(ResourceLocation("forge", type.name + "s/" + name))

    fun getSecondTag(type: ObjectType) = ItemTags.Wrapper(ResourceLocation("forge", type.name + "s/" + secondName))

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