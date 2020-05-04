package com.scientianova.versatile.materialsystem.properties

import com.scientianova.versatile.materialsystem.main.Form
import com.scientianova.versatile.materialsystem.main.GlobalForm
import com.scientianova.versatile.materialsystem.main.Material
import net.minecraft.util.ResourceLocation


data class MatProperty<T>(
        val name: ResourceLocation,
        val isValid: (T) -> Boolean = { true },
        val defaultFun: Material.() -> T
) {
    override fun hashCode() = name.hashCode()

    override fun equals(other: Any?) = other is MatProperty<*> && other.name == name
}

data class GlobalFormProperty<T>(
        val name: ResourceLocation,
        val isValid: (T) -> Boolean = { true },
        val defaultFun: GlobalForm.() -> T
) {
    override fun hashCode() = name.hashCode()

    override fun equals(other: Any?) = other is FormProperty<*> && other.name == name
}

data class FormProperty<T>(
        val name: ResourceLocation,
        val isValid: (T) -> Boolean = { true },
        val defaultFun: Form.() -> T
) {
    override fun hashCode() = name.hashCode()

    override fun equals(other: Any?) = other is FormProperty<*> && other.name == name
}