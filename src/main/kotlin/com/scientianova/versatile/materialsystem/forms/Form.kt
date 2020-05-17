package com.scientianova.versatile.materialsystem.forms

import com.scientianova.versatile.common.extensions.toResLoc
import com.scientianova.versatile.materialsystem.materials.Material
import com.scientianova.versatile.materialsystem.properties.*
import net.minecraft.fluid.FlowingFluid
import net.minecraft.tags.BlockTags
import net.minecraft.tags.FluidTags
import net.minecraft.tags.ItemTags
import net.minecraft.util.text.TranslationTextComponent

class GlobalForm {
    private val forMaterials = mutableMapOf<Material, Form?>()
    private val properties = mutableMapOf<GlobalFormProperty<out Any?>, Any?>()
    private val defaults = mutableMapOf<FormProperty<out Any?>, Form.() -> Any?>()

    val specialized get() = forMaterials.values.filterNotNull()

    operator fun get(mat: Material) =
            if (mat in forMaterials) forMaterials[mat]
            else (if (generate(mat)) Form(mat, this) else null)?.also { forMaterials[mat] = it }

    operator fun <T> set(property: GlobalFormProperty<T>, value: T) {
        if (property.isValid(value)) properties[property] = value
        else error("Invalid value for property: $property")
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(property: GlobalFormProperty<T>) =
            if (property in properties) properties[property] as T
            else property.defaultFun(this).also { this[property] = it }

    operator fun contains(property: GlobalFormProperty<*>) = property in properties

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(property: FormProperty<T>) = defaults[property] as? Form.() -> T

    operator fun <T> set(property: FormProperty<T>, value: Form.() -> T) {
        defaults[property] = value
    }

    operator fun <T> FormProperty<T>.invoke(value: Form.() -> T) {
        defaults[this] = value
    }

    internal fun merge(other: GlobalForm): GlobalForm {
        other.generate = { generate(it) || other.generate(it) }
        other.forMaterials.forEach { (mat, form) ->
            if (form == null) return@forEach
            forMaterials[mat]?.merge(form) ?: run { forMaterials[mat] = form }
        }
        properties += other.properties
        defaults += other.defaults
        return this
    }

    var name
        get() = this[NAME]
        set(value) {
            this[NAME] = value
        }

    var bucketVolume
        get() = this[BUCKET_VOLUME]
        set(value) {
            this[BUCKET_VOLUME] = value
        }

    var generate
        get() = this[PREDICATE]
        set(value) {
            this[PREDICATE] = value
        }

    var indexBlacklist
        get() = this[INDEX_BLACKLIST]
        set(value) {
            this[INDEX_BLACKLIST] = value
        }

    var singleTextureSet
        get() = this[SINGLE_TEXTURE_SET]
        set(value) {
            this[SINGLE_TEXTURE_SET] = value
        }

    var itemTagName
        get() = this[ITEM_TAG]
        set(value) {
            this[ITEM_TAG] = value
        }

    var blockTagName
        get() = this[BLOCK_TAG]
        set(value) {
            this[BLOCK_TAG] = value
        }

    override fun toString() = name

    val itemTag get() = ItemTags.Wrapper(itemTagName.toResLoc())

    val blockTag get() = ItemTags.Wrapper(itemTagName.toResLoc())
}

class Form(val mat: Material, val global: GlobalForm) {
    private val properties = mutableMapOf<FormProperty<out Any?>, Any?>()

    internal fun merge(other: Form) {
        properties += other.properties
    }

    operator fun <T> set(property: FormProperty<T>, value: T) {
        if (property.isValid(value)) properties[property] = value
        else error("Invalid value for property: $property")
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(property: FormProperty<T>) =
            if (property in properties) properties[property] as T
            else (global[property] ?: property.defaultFun)(this).also { this[property] = it }

    operator fun contains(property: FormProperty<*>) = property in properties

    var registryName
        get() = this[REGISTRY_NAME]
        set(value) {
            this[REGISTRY_NAME] = value
        }

    var renderType
        get() = this[RENDER_TYPE]
        set(value) {
            this[RENDER_TYPE] = value
        }

    var alreadyImplemented
        get() = this[ALREADY_IMPLEMENTED]
        set(value) {
            this[ALREADY_IMPLEMENTED] = value
        }

    var itemTagNames
        get() = this[COMBINED_ITEM_TAGS]
        set(value) {
            this[COMBINED_ITEM_TAGS] = value
        }

    var blockTagNames
        get() = this[COMBINED_BLOCK_TAGS]
        set(value) {
            this[COMBINED_BLOCK_TAGS] = value
        }

    var fluidTagNames
        get() = this[COMBINED_FLUID_TAGS]
        set(value) {
            this[COMBINED_FLUID_TAGS] = value
        }

    var color
        get() = this[FORM_COLOR]
        set(value) {
            this[FORM_COLOR] = value
        }

    var densityMultiplier
        get() = this[FORM_DENSITY_MULTIPLIER]
        set(value) {
            this[FORM_DENSITY_MULTIPLIER] = value
        }

    var temperature
        get() = this[TEMPERATURE]
        set(value) {
            this[TEMPERATURE] = value
        }

    var burnTime
        get() = this[BURN_TIME]
        set(value) {
            this[BURN_TIME] = value
        }

    var itemModel
        get() = this[ITEM_MODEL]
        set(value) {
            this[ITEM_MODEL] = value
        }

    var blockStateJSON
        get() = this[BLOCKSTATE_JSON]
        set(value) {
            this[BLOCKSTATE_JSON] = value
        }

    var item
        get() = this[ITEM]
        set(value) {
            this[ITEM] = value
            alreadyImplemented = true
        }

    var block
        get() = this[BLOCK]
        set(value) {
            this[BLOCK] = value
            value?.asItem()?.let { item = it }
            alreadyImplemented = true
        }

    var fluidProperties
        get() = this[FLUID_PROPERTIES]
        set(value) {
            this[FLUID_PROPERTIES] = value
        }

    var stillFluid: FlowingFluid?
        get() = this[STILL_FLUID]
        set(value) {
            this[STILL_FLUID] = value
            alreadyImplemented = true
        }

    var flowingFluid
        get() = this[FLOWING_FLUID]
        set(value) {
            this[FLOWING_FLUID] = value
            alreadyImplemented = true
        }

    val itemTags get() = itemTagNames.map { ItemTags.Wrapper(it.toResLoc()) }

    val blockTags get() = blockTagNames.map { BlockTags.Wrapper(it.toResLoc()) }

    val fluidTags get() = fluidTagNames.map { FluidTags.Wrapper(it.toResLoc()) }

    operator fun invoke(builder: Form.() -> Unit) = builder(this)

    fun localize() = TranslationTextComponent("form.$global", mat.localizedName)

    override fun toString() = global.name
}