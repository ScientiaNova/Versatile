package com.emosewapixel.pixellib.materialsystem.properties

import com.emosewapixel.pixellib.materialsystem.materials.Material
import com.emosewapixel.pixellib.materialsystem.types.ObjectType
import net.minecraft.util.ResourceLocation

sealed class Property<T, S> {
    abstract val name: ResourceLocation
    abstract val default: (S) -> T
}

data class MatProperty<T>(override val name: ResourceLocation, override val default: (Material) -> T) : Property<T, Material>() {
    override fun hashCode() = name.hashCode()

    override fun equals(other: Any?) = other is MatProperty<*> && other.name == name
}

data class ObjTypeProperty<T>(override val name: ResourceLocation, override val default: (ObjectType<*, *>) -> T) : Property<T, ObjectType<*, *>>() {
    override fun hashCode() = name.hashCode()

    override fun equals(other: Any?) = other is ObjTypeProperty<*> && other.name == name
}