package com.scientianova.versatile.materialsystem.properties

import com.scientianova.versatile.materialsystem.forms.Form
import com.scientianova.versatile.materialsystem.forms.FormInstance
import com.scientianova.versatile.materialsystem.materials.Material
import net.minecraft.util.ResourceLocation

open class Property<T : Any, S>(
        val name: ResourceLocation,
        protected val default: (T) -> S,
        val predicate: (S) -> Boolean
) {
    protected val lazyValues = mutableMapOf<T, (T) -> S>()
    protected val values = mutableMapOf<T, S>()

    operator fun set(thing: T, value: (T) -> S) = lazyValues.set(thing, value)

    open operator fun get(thing: T): S = if (thing in values) values[thing]!! else {
        val fn = if (thing in lazyValues) lazyValues[thing]!! else default
        fn(thing).also { assert(predicate(it)) { "Invalid $name for $thing" } }
    }
}

class MatProperty<T>(
        name: ResourceLocation,
        predicate: (T) -> Boolean = { true },
        default: Material.() -> T
) : Property<Material, T>(name, default, predicate)

class FormProperty<T>(
        name: ResourceLocation,
        predicate: (T) -> Boolean = { true },
        default: Form.() -> T
) : Property<Form, T>(name, default, predicate)

open class FormInstanceProperty<T>(
        name: ResourceLocation,
        predicate: (T) -> Boolean = { true },
        default: FormInstance.() -> T
) : Property<FormInstance, T>(name, default, predicate) {
    protected val lazyForForms = mutableMapOf<Form, (FormInstance) -> T>()

    operator fun set(mat: Material, form: Form, value: (FormInstance) -> T) =
            lazyValues.set(FormInstance(mat, form), value)

    operator fun set(form: Form, value: (FormInstance) -> T) = lazyForForms.set(form, value)

    operator fun get(mat: Material, form: Form) = get(FormInstance(mat, form))

    override fun get(thing: FormInstance): T = if (thing in values) values[thing]!! else {
        val fn = when {
            thing in lazyValues -> lazyValues[thing]!!
            thing.form in lazyForForms -> lazyForForms[thing.form]!!
            else -> default
        }
        fn(thing).also { assert(predicate(it)) { "Invalid $name for $thing" } }
    }
}

open class RegistryProperty<T>(
        name: ResourceLocation,
        predicate: (T?) -> Boolean = { true },
        default: FormInstance.() -> T? = { null }
) : FormInstanceProperty<T?>(name, predicate, default) {
    override fun get(thing: FormInstance): T? = when {
        thing in values -> values[thing]!!
        thing.form[matPredicate](thing.mat) -> null.also { values[thing] = null }
        else -> {
            val fn = when {
                thing in lazyValues -> lazyValues[thing]!!
                thing.form in lazyForForms -> lazyForForms[thing.form]!!
                else -> default
            }
            fn(thing).also { assert(predicate(it)) { "Invalid $name for $thing" } }
        }
    }
}