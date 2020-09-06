package com.scientianova.versatile.materialsystem.forms

import com.scientianova.versatile.materialsystem.materials.Material
import com.scientianova.versatile.materialsystem.properties.FormInstanceProperty
import com.scientianova.versatile.materialsystem.properties.FormProperty
import net.minecraft.util.text.TranslationTextComponent

data class Form(val name: String) {
    operator fun <T> get(property: FormProperty<T>) = property[this]

    operator fun <T> set(property: FormProperty<T>, value: Form.() -> T) =
            this.also { property[this] = value }

    operator fun <T> set(property: FormInstanceProperty<T>, value: FormInstance.() -> T) =
            this.also { property[this] = value }
}

data class FormInstance(val mat: Material, val form: Form) {
    fun localize() = TranslationTextComponent("form.$form", mat.localizedName)

    operator fun <T> get(property: FormInstanceProperty<T>) = property[this]

    operator fun <T> set(property: FormInstanceProperty<T>, value: FormInstance.() -> T) =
            this.also { property[this] = value }
}