package com.scientianovateam.versatile.materialsystem.elements

import com.scientianovateam.versatile.materialsystem.lists.ELEMENTS
import com.scientianovateam.versatile.materialsystem.main.Form
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.materialsystem.properties.CompoundType

//This class contains functions used for determining the elemental properties of compounds
object ElementUtils {
    @JvmStatic
    fun getElementalComposition(mat: Material): List<ElementStack> {
        if (mat.composition.isEmpty())
            return listOf(mat.element.toStack())

        val map = mat.fullComposition.groupBy { it.material.element }.mapValues { (_, value) -> value.map { it.count }.sum() }

        return if (ELEMENTS["null"] in map) listOf(ElementStack.EMPTY) else map.entries.map { it.key * it.value }
    }

    @JvmStatic
    fun getTotalProtons(mat: Material) = getElementalComposition(mat).map { (element, count) -> element.protons * count }.sum()

    @JvmStatic
    fun getTotalNeutrons(mat: Material) = getElementalComposition(mat).map { (element, count) -> element.neutrons * count }.sum()

    @JvmStatic
    fun getMolarMass(mat: Material) = getElementalComposition(mat).map { (element, count) -> element.atomicMass * count }.sum()

    @JvmStatic
    fun getTotalDensity(mat: Material, form: Form): Double {
        val list = getElementalComposition(mat).map { (element, count) -> element.density * count }
        return (if (mat.compoundType === CompoundType.CHEMICAL) list.sum() else list.average()) * mat.densityMultiplier * form.densityMultiplier(mat)
    }
}