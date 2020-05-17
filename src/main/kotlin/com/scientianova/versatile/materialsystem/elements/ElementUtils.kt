package com.scientianova.versatile.materialsystem.elements

import com.scientianova.versatile.materialsystem.forms.Form
import com.scientianova.versatile.materialsystem.materials.Material
import com.scientianova.versatile.materialsystem.properties.CompoundType

fun getElementalComposition(mat: Material): List<ElementStack> {
    if (mat.composition.isEmpty())
        return listOf(mat.element.toStack())

    val map = mat.fullComposition.groupBy { it.material.element }.mapValues { (_, value) -> value.map { it.count }.sum() }

    return if (NULL in map) listOf(ElementStack.EMPTY) else map.entries.map { it.key * it.value }
}

fun getTotalProtons(mat: Material) = getElementalComposition(mat).map { (element, count) -> element.protons * count }.sum()

fun getTotalNeutrons(mat: Material) = getElementalComposition(mat).map { (element, count) -> element.neutrons * count }.sum()

fun getMolarMass(mat: Material) = getElementalComposition(mat).map { (element, count) -> element.atomicMass * count }.sum()

fun getTotalDensity(mat: Material, type: Form): Double {
    val list = getElementalComposition(mat).map { (element, count) -> element.density * count }
    return (if (mat.compoundType === CompoundType.CHEMICAL) list.sum() else list.average()) * type.densityMultiplier * mat.densityMultiplier
}