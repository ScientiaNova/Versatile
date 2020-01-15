package com.scientianovateam.versatile.velisp.unresolved

import com.google.gson.JsonPrimitive
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.materialsystem.properties.IPropertyContainer
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.unevaluated.IUnevaluated

data class Getter(val name: String) : IUnresolved {
    override fun resolve(map: Map<String, IEvaluated>): IUnevaluated {
        val stringIterator = name.split('/').iterator()
        val currentProperty = stringIterator.next().let { map[it] ?: error("Unknown variable name in getter $it") }
        return if (stringIterator.hasNext()) {
            (currentProperty as? IPropertyContainer)?.let {
                findEndProperty(it, stringIterator, stringIterator.next().toResLocV().toString())
            } ?: error("Couldn't get end property for $name")
        } else currentProperty
    }

    override fun tryToResolve(map: Map<String, IEvaluated>) = if (name.takeWhile { it != '/' } in map) resolve(map) else this

    private fun findEndProperty(property: IPropertyContainer, iterator: Iterator<String>, currentName: String): IEvaluated? =
            property.properties[currentName]?.let { newProperty ->
                if (iterator.hasNext()) findEndProperty(newProperty as? IPropertyContainer ?: return null,
                        iterator, iterator.next().toResLocV().toString())
                else newProperty
            }

    override fun serialize() = JsonPrimitive("$$name")
}