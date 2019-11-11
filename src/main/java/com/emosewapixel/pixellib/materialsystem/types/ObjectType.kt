package com.emosewapixel.pixellib.materialsystem.types

import com.blamejared.crafttweaker.api.annotations.ZenRegister
import com.emosewapixel.pixellib.materialsystem.lists.ObjTypes
import com.emosewapixel.pixellib.materialsystem.materials.DustMaterial
import com.emosewapixel.pixellib.materialsystem.materials.Material
import com.emosewapixel.pixellib.materialsystem.materials.utility.ct.MaterialRequirement
import net.minecraft.item.Item
import net.minecraft.tags.ItemTags
import net.minecraft.tags.Tag
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import org.openzen.zencode.java.ZenCodeType

/*
Object Types are objects used for generating blocks/items/fluids for certain materials.
This is the base class that shouldn't be used for generating anything
*/
@ZenRegister
@ZenCodeType.Name("pixellib.materialsystem.types.ObjectType")
abstract class ObjectType<O, T : ObjectType<O, T>> @ZenCodeType.Constructor
constructor(@ZenCodeType.Field val name: String,
            @ZenCodeType.Field var requirement: MaterialRequirement,
            @ZenCodeType.Field val objectConstructor: (Material, T) -> O) {

    @ZenCodeType.Field
    var bucketVolume = 0

    val typeTags = mutableListOf<String>()
        @ZenCodeType.Getter get

    val indexBlackList = mutableListOf<Int>()
        @ZenCodeType.Getter get

    val extraProperties = mutableMapOf<String, Any>()
        @ZenCodeType.Getter get

    @ZenCodeType.Field
    var buildRegistryName: (Material) -> ResourceLocation = { ResourceLocation("pixellib:${it.name}_$name") }

    @ZenCodeType.Field
    var buildTagName: (String) -> ResourceLocation = { ResourceLocation("forge", "${name}s/$it") }

    @ZenCodeType.Field
    var color: (Material) -> Int = Material::color

    @ZenCodeType.Field
    var densityMultiplier = 1f

    val itemTag: Tag<Item>
        @ZenCodeType.Getter get() = ItemTags.Wrapper(ResourceLocation("forge", name + "s"))

    @Suppress("UNCHECKED_CAST")
    operator fun invoke(builder: T.() -> Unit) = builder(this as T)

    abstract fun localize(mat: Material): ITextComponent

    @ZenCodeType.Method
    infix fun hasTag(tag: String) = tag in typeTags

    override fun toString() = name

    @ZenCodeType.Method
    fun isMaterialCompatible(mat: Material) = requirement.test(mat)

    open fun merge(type: ObjectType<*, *>) {
        if (type.bucketVolume != 0)
            bucketVolume = type.bucketVolume
        requirement = MaterialRequirement { requirement.and(type.requirement).test(it) }
        typeTags.addAll(type.typeTags)
        extraProperties.putAll(type.extraProperties)
    }

    @ZenCodeType.Method
    fun build(): ObjectType<*, *> {
        ObjTypes.add(this)
        return ObjTypes[name]!!
    }

    companion object {
        @JvmStatic
        fun getUnrefinedColor(mat: Material) = (mat as? DustMaterial)?.unrefinedColor ?: mat.color
    }
}