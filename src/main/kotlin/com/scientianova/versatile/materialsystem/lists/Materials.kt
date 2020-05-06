@file:JvmName("Materials")

package com.scientianova.versatile.materialsystem.lists

import com.scientianova.versatile.materialsystem.main.Material
import net.minecraft.item.Item

private val materials = hashMapOf<String, Material>()

val allMaterials
    get() = materials.values.distinct()

fun addMaterial(mat: Material): Material {
    val merged = mat.associatedNames.mapNotNull(::materialFor).distinct()
            .reduceOrNull(Material::merge)?.merge(mat) ?: mat
    mat.associatedNames.forEach { materials[it] = merged }
    return merged
}

fun materialFor(name: String) = materials[name]

fun materialExists(name: String) = name in materials

inline fun <S, T : S> Iterable<T>.reduceOrNull(operation: (acc: S, T) -> S): S? {
    val iterator = iterator()
    if (!iterator.hasNext()) return null
    var accumulator: S = iterator.next()
    while (iterator.hasNext()) {
        accumulator = operation(accumulator, iterator.next())
    }
    return accumulator
}

val Item.material
    get() = tags.asSequence()
            .filter { '/' in it.path }.map { materialFor(it.path.takeLastWhile { char -> char != '/' }) }.firstOrNull()