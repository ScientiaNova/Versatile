package com.scientianova.versatile.materialsystem.materials

import com.scientianova.versatile.materialsystem.properties.MatProperty
import net.minecraft.util.text.TranslationTextComponent

data class Material(val name: String) {
    operator fun <T> get(property: MatProperty<T>) = property[this]

    operator fun <T> set(property: MatProperty<T>, value: Material.() -> T) =
            this.also { property[this] = value }

    operator fun times(count: Int) = MaterialStack(this, count)

    @JvmOverloads
    fun toStack(count: Int = 1) = MaterialStack(this, count)

    val localizedName get() = TranslationTextComponent("material.$name")
}