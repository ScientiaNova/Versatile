package com.scientianovateam.versatile.materialsystem.main

import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.materialsystem.addition.FormProperties
import com.scientianovateam.versatile.materialsystem.addition.LegacyFormProperties
import com.scientianovateam.versatile.materialsystem.lists.Forms
import com.scientianovateam.versatile.materialsystem.properties.FormLegacyProperty
import com.scientianovateam.versatile.materialsystem.properties.IPropertyContainer
import com.scientianovateam.versatile.materialsystem.properties.Property
import com.scientianovateam.versatile.velisp.evaluated.BoolValue
import com.scientianovateam.versatile.velisp.evaluated.IEvaluated
import com.scientianovateam.versatile.velisp.evaluated.StringValue
import net.minecraft.tags.ItemTags
import net.minecraft.util.text.TranslationTextComponent

class Form(val properties: Map<Material, Map<String, IEvaluated>>) {
    val any = properties.values.iterator().next()

    val legacyProperties = mutableMapOf<FormLegacyProperty<out Any?>, Any?>()

    val name = (any[FormProperties.NAME] as StringValue).toString()
    val requirement: (Material) -> Boolean = {
        (properties[it]?.get(FormProperties.GENERATE) as? BoolValue)?.value ?: false
    }

    constructor(name: String, requirement: (Material) -> Boolean) : this(mutableMapOf()) {
        // TODO legacy constructor
    }

    operator fun <T> set(property: FormLegacyProperty<T>, value: T) {
        if (property.isValid(value)) legacyProperties[property] = value
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(property: FormLegacyProperty<T>) = legacyProperties[property] as? T ?: property.default(this)

    operator fun contains(property: FormLegacyProperty<*>) = property in legacyProperties

    val indexBlackList = mutableListOf<Int>()

    var bucketVolume
        get() = this[LegacyFormProperties.BUCKET_VOLUME]
        set(value) {
            this[LegacyFormProperties.BUCKET_VOLUME] = value
        }

    var registryName
        get() = this[LegacyFormProperties.REGISTRY_NAME_FUM]
        set(value) {
            this[LegacyFormProperties.REGISTRY_NAME_FUM] = value
        }

    var itemTagName
        get() = this[LegacyFormProperties.ITEM_TAG]
        set(value) {
            this[LegacyFormProperties.ITEM_TAG] = value
        }

    var blockTagName
        get() = this[LegacyFormProperties.BLOCK_TAG]
        set(value) {
            this[LegacyFormProperties.BLOCK_TAG] = value
        }

    var fluidTagName
        get() = this[LegacyFormProperties.FLUID_TAG]
        set(value) {
            this[LegacyFormProperties.FLUID_TAG] = value
        }

    var color
        get() = this[LegacyFormProperties.COLOR_FUN]
        set(value) {
            this[LegacyFormProperties.COLOR_FUN] = value
        }

    var densityMultiplier
        get() = this[LegacyFormProperties.DENSITY_MULTIPLIER]
        set(value) {
            this[LegacyFormProperties.DENSITY_MULTIPLIER] = value
        }

    var isGas
        get() = this[LegacyFormProperties.IS_GAS]
        set(value) {
            this[LegacyFormProperties.IS_GAS] = value
        }

    var temperature
        get() = this[LegacyFormProperties.TEMPERATURE]
        set(value) {
            this[LegacyFormProperties.TEMPERATURE] = value
        }

    var singleTextureType
        get() = this[LegacyFormProperties.SINGLE_TEXTURE_TYPE]
        set(value) {
            this[LegacyFormProperties.SINGLE_TEXTURE_TYPE] = value
        }

    var burnTime
        get() = this[LegacyFormProperties.BURN_TIME]
        set(value) {
            this[LegacyFormProperties.BURN_TIME] = value
        }

    var itemModel
        get() = this[LegacyFormProperties.ITEM_MODEL]
        set(value) {
            this[LegacyFormProperties.ITEM_MODEL] = value
        }

    var blockStateJSON
        get() = this[LegacyFormProperties.BLOCKSTATE_JSON]
        set(value) {
            this[LegacyFormProperties.BLOCKSTATE_JSON] = value
        }

    var itemConstructor
        get() = this[LegacyFormProperties.ITEM_CONSTRUCTOR]
        set(value) {
            this[LegacyFormProperties.ITEM_CONSTRUCTOR] = value
        }

    var itemProperties
        get() = this[LegacyFormProperties.ITEM_PROPERTIES]
        set(value) {
            this[LegacyFormProperties.ITEM_PROPERTIES] = value
        }

    var blockConstructor
        get() = this[LegacyFormProperties.BLOCK_CONSTRUCTOR]
        set(value) {
            this[LegacyFormProperties.BLOCK_CONSTRUCTOR] = value
        }

    var blockProperties
        get() = this[LegacyFormProperties.BLOCK_PROPERTIES]
        set(value) {
            this[LegacyFormProperties.BLOCK_PROPERTIES] = value
        }

    var fluidPairConstructor
        get() = this[LegacyFormProperties.FLUID_CONSTRUCTOR]
        set(value) {
            this[LegacyFormProperties.FLUID_CONSTRUCTOR] = value
        }

    var fluidAttributes
        get() = this[LegacyFormProperties.FLUID_ATTRIBUTES]
        set(value) {
            this[LegacyFormProperties.FLUID_ATTRIBUTES] = value
        }

    var typePriority
        get() = this[LegacyFormProperties.TYPE_PRIORITY]
        set(value) {
            this[LegacyFormProperties.TYPE_PRIORITY] = value
        }

    val itemTag get() = ItemTags.Wrapper(itemTagName.toResLoc())

    val blockTag get() = ItemTags.Wrapper(itemTagName.toResLoc())

    operator fun invoke(builder: Form.() -> Unit) = builder(this)

    fun localize(mat: Material) = TranslationTextComponent("form.$name", mat.localizedName)

    override fun toString() = name

    fun isMaterialCompatible(mat: Material) = requirement(mat)

    fun merge(type: Form): Form {
        type.legacyProperties.forEach { (key, value) ->
            key.merge(legacyProperties[key], value)?.let { legacyProperties[key] = it }
        }
        //requirement = { requirement(it) && type.requirement(it) }
        return this
    }

    fun register(): Form {
        Forms.add(this)
        return Forms[name]!!
    }
}