package com.emosewapixel.pixellib.materialsystem.element

import com.emosewapixel.pixellib.materialsystem.materials.CompoundType
import com.emosewapixel.pixellib.materialsystem.materials.Material

//This class contains functions used for determining the elemental properties of compounds
object ElementUtils {
    @JvmStatic
    fun getElementalComposition(mat: Material): List<ElementStack> {
        if (mat.composition.isEmpty())
            return listOf(ElementStack(mat.element))

        val map = mat.fullComposition.groupBy { it.material.element }.mapValues { (_, value) -> value.map { it.count }.sum() }

        return if (Elements.NULL in map) listOf(ElementStack(Elements.NULL)) else map.entries.map { ElementStack(it.key, it.value) }
    }

    @JvmStatic
    fun getTotalProtons(mat: Material) = getElementalComposition(mat).map { (element, count) -> element.protons * count }.sum()

    @JvmStatic
    fun getTotalNeutrons(mat: Material) = getElementalComposition(mat).map { (element, count) -> element.neutrons * count }.sum()

    @JvmStatic
    fun getMolarMass(mat: Material) = getElementalComposition(mat).map { (element, count) -> element.atomicMass * count }.sum()

    @JvmStatic
    fun getTotalDensity(mat: Material): Double {
        val stream = getElementalComposition(mat).map { (element, count) -> element.density * count }
        return if (mat.compoundType === CompoundType.CHEMICAL) stream.sum() else stream.average()
    }
}