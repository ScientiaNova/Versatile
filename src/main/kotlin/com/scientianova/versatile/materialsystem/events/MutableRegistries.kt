package com.scientianova.versatile.materialsystem.events

import com.scientianova.versatile.common.registry.MutableStringRegistry
import com.scientianova.versatile.materialsystem.elements.Element
import com.scientianova.versatile.materialsystem.forms.GlobalForm
import com.scientianova.versatile.materialsystem.materials.Material

class ElementRegistry: MutableStringRegistry<Element> {
    internal val map = mutableMapOf<String, Element>()

    override fun get(name: String) = map[name]

    override fun register(thing: Element): Element {
        map[thing.name] = thing
        return thing
    }
}

class MaterialRegistry: MutableStringRegistry<Material> {
    internal val map = mutableMapOf<String, Material>()

    override fun get(name: String) = map[name]

    override fun register(thing: Material): Material {
        val merged = thing.associatedNames.mapNotNull(::get).distinct()
                .reduceOrNull(Material::merge)?.merge(thing) ?: thing
        thing.associatedNames.forEach { map[it] = merged }
        return merged
    }
}

class FormRegistry: MutableStringRegistry<GlobalForm> {
    internal val map = mutableMapOf<String, GlobalForm>()

    override fun get(name: String) = map[name]

    override fun register(thing: GlobalForm) = map[thing.name]?.merge(thing) ?: thing.also { map[thing.name] = thing }
}

inline fun <S, T : S> Iterable<T>.reduceOrNull(operation: (acc: S, T) -> S): S? {
    val iterator = iterator()
    if (!iterator.hasNext()) return null
    var accumulator: S = iterator.next()
    while (iterator.hasNext()) {
        accumulator = operation(accumulator, iterator.next())
    }
    return accumulator
}