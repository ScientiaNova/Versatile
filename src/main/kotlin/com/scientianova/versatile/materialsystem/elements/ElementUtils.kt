package com.scientianova.versatile.materialsystem.elements

import com.scientianova.versatile.materialsystem.forms.FormInstance
import com.scientianova.versatile.materialsystem.materials.Material
import com.scientianova.versatile.materialsystem.materials.MaterialStack
import com.scientianova.versatile.materialsystem.properties.*

val Material.fullComposition: List<MaterialStack>
    get() = if (get(composition).isEmpty()) listOf(this.toStack()) else get(composition).flatMap { (material, count) ->
        material.fullComposition.map { (material1, count1) -> material1 * (count1 * count) }
    }

fun getElementalComposition(mat: Material): List<ElementStack> {
    if (mat[composition].isEmpty())
        return listOf(mat[element].toStack())

    val map = mat.fullComposition.groupBy { it.material[element] }.mapValues { (_, value) -> value.map { it.count }.sum() }

    return if (nullElem in map) listOf(ElementStack.EMPTY) else map.entries.map { it.key * it.value }
}

fun getTotalProtons(mat: Material) = getElementalComposition(mat).map { (element, count) -> element.protons * count }.sum()

fun getTotalNeutrons(mat: Material) = getElementalComposition(mat).map { (element, count) -> element.neutrons * count }.sum()

fun getMolarMass(mat: Material) = getElementalComposition(mat).map { (element, count) -> element.atomicMass * count }.sum()

fun getTotalDensity(mat: Material, form: FormInstance): Double {
    val list = getElementalComposition(mat).map { (element, count) -> element.density * count }
    return (if (mat[isAlloy]) list.sum() else list.average()) * form[formDensityMultiplier] * mat[densityMultiplier]
}