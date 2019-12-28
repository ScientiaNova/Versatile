package com.scientianovateam.versatile.materialsystem.main

import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.materialsystem.addition.FormProperties
import com.scientianovateam.versatile.materialsystem.lists.Forms
import com.scientianovateam.versatile.materialsystem.properties.FormLegacyProperty
import net.minecraft.tags.ItemTags
import net.minecraft.util.text.TranslationTextComponent

class Form(val name: String, var requirement: (Material) -> Boolean) {
    val properties = mutableMapOf<FormLegacyProperty<out Any?>, Any?>()

    operator fun <T> set(property: FormLegacyProperty<T>, value: T) {
        if (property.isValid(value)) properties[property] = value
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(property: FormLegacyProperty<T>) = properties[property] as? T ?: property.default(this)

    operator fun contains(property: FormLegacyProperty<*>) = property in properties

    val indexBlackList = mutableListOf<Int>()

    var bucketVolume
        get() = this[FormProperties.BUCKET_VOLUME]
        set(value) {
            this[FormProperties.BUCKET_VOLUME] = value
        }

    var registryName
        get() = this[FormProperties.REGISTRY_NAME_FUM]
        set(value) {
            this[FormProperties.REGISTRY_NAME_FUM] = value
        }

    var itemTagName
        get() = this[FormProperties.ITEM_TAG]
        set(value) {
            this[FormProperties.ITEM_TAG] = value
        }

    var blockTagName
        get() = this[FormProperties.BLOCK_TAG]
        set(value) {
            this[FormProperties.BLOCK_TAG] = value
        }

    var fluidTagName
        get() = this[FormProperties.FLUID_TAG]
        set(value) {
            this[FormProperties.FLUID_TAG] = value
        }

    var color
        get() = this[FormProperties.COLOR_FUN]
        set(value) {
            this[FormProperties.COLOR_FUN] = value
        }

    var densityMultiplier
        get() = this[FormProperties.DENSITY_MULTIPLIER]
        set(value) {
            this[FormProperties.DENSITY_MULTIPLIER] = value
        }

    var isGas
        get() = this[FormProperties.IS_GAS]
        set(value) {
            this[FormProperties.IS_GAS] = value
        }

    var temperature
        get() = this[FormProperties.TEMPERATURE]
        set(value) {
            this[FormProperties.TEMPERATURE] = value
        }

    var singleTextureType
        get() = this[FormProperties.SINGLE_TEXTURE_TYPE]
        set(value) {
            this[FormProperties.SINGLE_TEXTURE_TYPE] = value
        }

    var burnTime
        get() = this[FormProperties.BURN_TIME]
        set(value) {
            this[FormProperties.BURN_TIME] = value
        }

    var itemModel
        get() = this[FormProperties.ITEM_MODEL]
        set(value) {
            this[FormProperties.ITEM_MODEL] = value
        }

    var blockStateJSON
        get() = this[FormProperties.BLOCKSTATE_JSON]
        set(value) {
            this[FormProperties.BLOCKSTATE_JSON] = value
        }

    var itemConstructor
        get() = this[FormProperties.ITEM_CONSTRUCTOR]
        set(value) {
            this[FormProperties.ITEM_CONSTRUCTOR] = value
        }

    var itemProperties
        get() = this[FormProperties.ITEM_PROPERTIES]
        set(value) {
            this[FormProperties.ITEM_PROPERTIES] = value
        }

    var blockConstructor
        get() = this[FormProperties.BLOCK_CONSTRUCTOR]
        set(value) {
            this[FormProperties.BLOCK_CONSTRUCTOR] = value
        }

    var blockProperties
        get() = this[FormProperties.BLOCK_PROPERTIES]
        set(value) {
            this[FormProperties.BLOCK_PROPERTIES] = value
        }

    var fluidPairConstructor
        get() = this[FormProperties.FLUID_CONSTRUCTOR]
        set(value) {
            this[FormProperties.FLUID_CONSTRUCTOR] = value
        }

    var fluidAttributes
        get() = this[FormProperties.FLUID_ATTRIBUTES]
        set(value) {
            this[FormProperties.FLUID_ATTRIBUTES] = value
        }

    var typePriority
        get() = this[FormProperties.TYPE_PRIORITY]
        set(value) {
            this[FormProperties.TYPE_PRIORITY] = value
        }

    val itemTag get() = ItemTags.Wrapper(itemTagName.toResLoc())

    val blockTag get() = ItemTags.Wrapper(itemTagName.toResLoc())

    operator fun invoke(builder: Form.() -> Unit) = builder(this)

    fun localize(mat: Material) = TranslationTextComponent("form.$name", mat.localizedName)

    override fun toString() = name

    fun isMaterialCompatible(mat: Material) = requirement(mat)

    fun merge(type: Form): Form {
        type.properties.forEach { (key, value) ->
            key.merge(properties[key], value)?.let { properties[key] = it }
        }
        requirement = { requirement(it) && type.requirement(it) }
        return this
    }

    fun register(): Form {
        Forms.add(this)
        return Forms[name]!!
    }
}