package com.scientianovateam.versatile.common.registry

import net.minecraftforge.eventbus.api.EventPriority

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class VersatileRegistry(val priority: EventPriority = EventPriority.NORMAL)