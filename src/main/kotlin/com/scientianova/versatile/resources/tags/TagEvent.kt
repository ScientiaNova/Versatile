package com.scientianova.versatile.resources.tags

import net.minecraftforge.eventbus.api.GenericEvent
import net.minecraftforge.registries.IForgeRegistryEntry

class TagEvent<T : IForgeRegistryEntry<T>>(val handler: TagHandler<T>, clazz: Class<T>) : GenericEvent<T>(clazz)

inline fun <reified T : IForgeRegistryEntry<T>> tagEvent(handler: TagHandler<T>) = TagEvent(handler, T::class.java)