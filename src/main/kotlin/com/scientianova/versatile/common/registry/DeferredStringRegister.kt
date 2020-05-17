package com.scientianova.versatile.common.registry

import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.IEventBus

interface DeferredStringRegister<T : Any> {
    val priority: EventPriority
    operator fun set(name: String, thing: () -> T): StringRegistryObject<T>
    fun register(bus: IEventBus)
}