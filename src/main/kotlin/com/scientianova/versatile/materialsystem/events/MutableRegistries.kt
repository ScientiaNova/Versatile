package com.scientianova.versatile.materialsystem.events

import com.scientianova.versatile.common.registry.MutableStringRegistry
import com.scientianova.versatile.materialsystem.elements.Element
import com.scientianova.versatile.materialsystem.forms.Form
import com.scientianova.versatile.materialsystem.materials.Material

class ElementRegistry : MutableStringRegistry<Element> {
    internal val map = mutableMapOf<String, Element>()

    override fun get(name: String) = map[name]

    override fun register(thing: Element) {
        map[thing.name] = thing
    }
}

class MaterialRegistry : MutableStringRegistry<Material> {
    internal val map = mutableMapOf<String, Material>()

    override fun get(name: String) = map[name]

    override fun register(thing: Material) {
        map[thing.name] = thing
    }
}

class FormRegistry : MutableStringRegistry<Form> {
    internal val map = mutableMapOf<String, Form>()

    override fun get(name: String) = map[name]

    override fun register(thing: Form) {
        map[thing.name] = thing
    }
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