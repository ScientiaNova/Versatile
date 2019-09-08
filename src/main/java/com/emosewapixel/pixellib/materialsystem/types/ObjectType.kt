package com.emosewapixel.pixellib.materialsystem.types

import com.emosewapixel.pixellib.materialsystem.lists.ObjTypes
import com.emosewapixel.pixellib.materialsystem.materials.Material
import net.minecraft.item.Item
import net.minecraft.tags.ItemTags
import net.minecraft.tags.Tag
import net.minecraft.util.ResourceLocation

/*
Object Types are objects used for generating blocks/items/fluids for certain materials.
This is the base class that shouldn't be used for generating anything
*/
open class ObjectType(val name: String, var requirement: (Material) -> Boolean) {
    var bucketVolume = 0
    val typeTags = mutableListOf<String>()

    val itemTag: Tag<Item>
        get() = ItemTags.Wrapper(ResourceLocation("forge", name + "s"))

    operator fun invoke(builder: ObjectType.() -> Unit) = builder(this)

    fun build(): ObjectType {
        ObjTypes.add(this)
        return ObjTypes[name]!!
    }

    fun isMaterialCompatible(mat: Material) = requirement(mat)

    open fun merge(type: ObjectType) {
        if (type.bucketVolume != 0)
            bucketVolume = type.bucketVolume
        requirement = { requirement(it) || type.isMaterialCompatible(it) }
        this.typeTags.addAll(type.typeTags)
    }
}