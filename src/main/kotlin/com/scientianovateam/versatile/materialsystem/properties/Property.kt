package com.scientianovateam.versatile.materialsystem.properties

import com.scientianovateam.versatile.common.registry.MATERIAL_PROPERTIES
import com.scientianovateam.versatile.common.registry.OBJECT_TYPE_PROPERTIES
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.materialsystem.main.Form
import net.minecraft.util.ResourceLocation

sealed class Property<T, S> {
    abstract val name: ResourceLocation
    abstract val default: (S) -> T
    abstract val isValid: (T) -> Boolean
    abstract val merge: (Any?, Any?) -> T?
}

data class MatProperty<T>(
        override val name: ResourceLocation,
        override val merge: (Any?, Any?) -> T?,
        override val isValid: (T) -> Boolean = { true },
        override val default: (Material) -> T
) : Property<T, Material>() {

    init {
        MATERIAL_PROPERTIES[name] = this
    }

    override fun hashCode() = name.hashCode()

    override fun equals(other: Any?) = other is MatProperty<*> && other.name == name
}

data class FormProperty<T>(
        override val name: ResourceLocation,
        override val merge: (Any?, Any?) -> T?,
        override val isValid: (T) -> Boolean = { true },
        override val default: (Form) -> T
) : Property<T, Form>() {

    init {
        OBJECT_TYPE_PROPERTIES[name] = this
    }

    override fun hashCode() = name.hashCode()

    override fun equals(other: Any?) = other is FormProperty<*> && other.name == name
}

inline fun <reified T> merge(first: Any?, second: Any?): T? = when (first) {
    null -> second as? T
    is T -> first
    else -> second as? T
}