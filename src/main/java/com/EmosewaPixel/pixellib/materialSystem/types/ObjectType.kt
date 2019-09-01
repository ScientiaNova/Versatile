package com.EmosewaPixel.pixellib.materialsystem.types

import com.EmosewaPixel.pixellib.materialsystem.lists.ObjTypes
import com.EmosewaPixel.pixellib.materialsystem.materials.Material
import net.minecraft.item.Item
import net.minecraft.tags.ItemTags
import net.minecraft.tags.Tag
import net.minecraft.util.ResourceLocation

import java.util.ArrayList
import java.util.Arrays
import java.util.function.Predicate

/*
Object Types are objects used for generating blocks/items/fluids for certain materials.
This is the base class that shouldn't be used for generating anything
*/
open class ObjectType(val name: String, private var requirement: Predicate<Material>) {
    private var volume = 0
    private val tags = mutableListOf<String>()

    val itemTag: Tag<Item>
        get() = ItemTags.Wrapper(ResourceLocation("forge", name + "s"))

    val typeTags: List<String>
        get() = tags

    fun addTypeTag(vararg tags: String): ObjectType {
        this.tags.addAll(Arrays.asList(*tags))
        return this
    }

    fun andRequires(requirement: Predicate<Material>): ObjectType {
        this.requirement = this.requirement.and(requirement)
        return this
    }

    fun orRequires(requirement: Predicate<Material>): ObjectType {
        this.requirement = this.requirement.or(requirement)
        return this
    }

    fun setBucketVolume(mb: Int): ObjectType {
        volume = mb
        return this
    }

    fun build(): ObjectType {
        ObjTypes.add(this)
        return ObjTypes[name]!!
    }

    fun getBucketVolume() = volume

    fun isMaterialCompatible(mat: Material): Boolean {
        return requirement.test(mat)
    }

    fun hasTag(tag: String) = tag in tags

    fun merge(type: ObjectType) {
        if (type.getBucketVolume() != 0)
            volume = type.getBucketVolume()
        orRequires(Predicate { type.isMaterialCompatible(it) })
        tags.addAll(type.typeTags)
    }
}