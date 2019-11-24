package com.emosewapixel.pixellib.materialsystem.main

import com.blamejared.crafttweaker.api.annotations.ZenRegister
import com.emosewapixel.pixellib.extensions.toResLoc
import com.emosewapixel.pixellib.materialsystem.lists.ObjTypes
import com.emosewapixel.pixellib.materialsystem.main.ct.MaterialRequirement
import com.emosewapixel.pixellib.materialsystem.properties.ObjTypeProperties
import com.emosewapixel.pixellib.materialsystem.properties.ObjTypeProperty
import net.minecraft.tags.ItemTags
import net.minecraft.util.text.TranslationTextComponent
import org.openzen.zencode.java.ZenCodeType

/*
Object Types are objects used for generating blocks/items/fluids for certain materials.
This is the base class that shouldn't be used for generating anything
*/
@ZenRegister
@ZenCodeType.Name("pixellib.materialsystem.main.ObjectType")
class ObjectType @ZenCodeType.Constructor constructor(
        @ZenCodeType.Field val name: String,
        @ZenCodeType.Field var requirement: MaterialRequirement
) {

    val properties = mutableMapOf<ObjTypeProperty<out Any?>, Any?>()

    operator fun <T> set(property: ObjTypeProperty<T>, value: T) {
        if (property.isValid(value)) properties[property] = value
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(property: ObjTypeProperty<T>) = properties[property] as? T ?: property.default(this)

    operator fun contains(property: ObjTypeProperty<*>) = property in properties

    val indexBlackList = mutableListOf<Int>()

    var bucketVolume
        get() = this[ObjTypeProperties.BUCKET_VOLUME]
        set(value) {
            this[ObjTypeProperties.BUCKET_VOLUME] = value
        }

    var registryName
        get() = this[ObjTypeProperties.REGISTRY_NAME_FUM]
        set(value) {
            this[ObjTypeProperties.REGISTRY_NAME_FUM] = value
        }

    var itemTagName
        get() = this[ObjTypeProperties.ITEM_TAG]
        set(value) {
            this[ObjTypeProperties.ITEM_TAG] = value
        }

    var blockTagName
        get() = this[ObjTypeProperties.BLOCK_TAG]
        set(value) {
            this[ObjTypeProperties.BLOCK_TAG] = value
        }

    var fluidTagName
        get() = this[ObjTypeProperties.FLUID_TAG]
        set(value) {
            this[ObjTypeProperties.FLUID_TAG] = value
        }

    var color
        get() = this[ObjTypeProperties.COLOR_FUN]
        set(value) {
            this[ObjTypeProperties.COLOR_FUN] = value
        }

    var densityMultiplier
        get() = this[ObjTypeProperties.DENSITY_MULTIPLIER]
        set(value) {
            this[ObjTypeProperties.DENSITY_MULTIPLIER] = value
        }

    var isGas
        get() = this[ObjTypeProperties.IS_GAS]
        set(value) {
            this[ObjTypeProperties.IS_GAS] = value
        }

    var temperature
        get() = this[ObjTypeProperties.TEMPERATURE]
        set(value) {
            this[ObjTypeProperties.TEMPERATURE] = value
        }

    var singleTextureType
        get() = this[ObjTypeProperties.SINGLE_TEXTURE_TYPE]
        set(value) {
            this[ObjTypeProperties.SINGLE_TEXTURE_TYPE] = value
        }

    var burnTime
        get() = this[ObjTypeProperties.BURN_TIME]
        set(value) {
            this[ObjTypeProperties.BURN_TIME] = value
        }

    var itemModel
        get() = this[ObjTypeProperties.ITEM_MODEL]
        set(value) {
            this[ObjTypeProperties.ITEM_MODEL] = value
        }

    var blockStateJSON
        get() = this[ObjTypeProperties.BLOCKSTATE_JSON]
        set(value) {
            this[ObjTypeProperties.BLOCKSTATE_JSON] = value
        }

    var itemConstructor
        get() = this[ObjTypeProperties.ITEM_CONSTRUCTOR]
        set(value) {
            this[ObjTypeProperties.ITEM_CONSTRUCTOR] = value
        }

    var itemProperties
        get() = this[ObjTypeProperties.ITEM_PROPERTIES]
        set(value) {
            this[ObjTypeProperties.ITEM_PROPERTIES] = value
        }

    var blockConstructor
        get() = this[ObjTypeProperties.BLOCK_CONSTRUCTOR]
        set(value) {
            this[ObjTypeProperties.BLOCK_CONSTRUCTOR] = value
        }

    var blockProperties
        get() = this[ObjTypeProperties.BLOCK_PROPERTIES]
        set(value) {
            this[ObjTypeProperties.BLOCK_PROPERTIES] = value
        }

    var fluidPairConstructor
        get() = this[ObjTypeProperties.FLUID_CONSTRUCTOR]
        set(value) {
            this[ObjTypeProperties.FLUID_CONSTRUCTOR] = value
        }

    var fluidAttributes
        get() = this[ObjTypeProperties.FLUID_ATTRIBUTES]
        set(value) {
            this[ObjTypeProperties.FLUID_ATTRIBUTES] = value
        }

    var typePriority
        get() = this[ObjTypeProperties.TYPE_PRIORITY]
        set(value) {
            this[ObjTypeProperties.TYPE_PRIORITY] = value
        }

    val itemTag get() = ItemTags.Wrapper(itemTagName.toResLoc())

    val blockTag get() = ItemTags.Wrapper(itemTagName.toResLoc())

    operator fun invoke(builder: ObjectType.() -> Unit) = builder(this)

    fun localize(mat: Material) = TranslationTextComponent("objtype.$name", mat.localizedName)

    override fun toString() = name

    fun isMaterialCompatible(mat: Material) = requirement.test(mat)

    fun merge(type: ObjectType) {
        type.properties.forEach { (key, value) ->
            key.merge(properties[key], value)?.let { properties[key] = it }
        }
        requirement = MaterialRequirement { requirement.and(type.requirement).test(it) }
    }

    fun register(): ObjectType {
        ObjTypes.add(this)
        return ObjTypes[name]!!
    }
}