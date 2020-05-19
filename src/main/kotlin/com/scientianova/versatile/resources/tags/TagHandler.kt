package com.scientianova.versatile.resources.tags

import com.scientianova.versatile.common.extensions.toResLoc
import net.minecraft.tags.Tag
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.IForgeRegistryEntry

class TagHandler<T : IForgeRegistryEntry<T>>(private val map: MutableMap<ResourceLocation, Tag<T>>) {
    fun addTo(tag: Tag<T>, vararg things: T) = things.forEach {
        tag.allElements.add(it)
        tag.entries.add(Tag.TagEntry(it.registryName!!))
    }

    fun addTo(name: ResourceLocation, vararg things: T) =
            map[name]?.let { addTo(it, *things) } ?: run {
                map[name] = Tag.Builder<T>().add(*things).build(name)
            }

    fun addTo(name: String, vararg things: T) = addTo(name.toResLoc(), *things)

    fun removeFrom(tag: Tag<T>, vararg things: T) = things.forEach {
        tag.allElements.remove(it)
        tag.entries.remove(Tag.TagEntry<T>(it.registryName!!))
    }

    fun removeFrom(name: ResourceLocation, vararg things: T) {
        map[name]?.let { removeFrom(it, *things) }
    }

    fun removeFrom(name: String, vararg things: T) = removeFrom(name.toResLoc(), *things)

    fun remove(tag: Tag<T>) = map.remove(tag.id)

    fun remove(name: ResourceLocation) = map.remove(name)

    fun remove(name: String) = map.remove(name.toResLoc())

}