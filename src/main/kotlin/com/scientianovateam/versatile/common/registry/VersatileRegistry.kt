package com.scientianovateam.versatile.common.registry

import net.minecraftforge.eventbus.api.EventPriority

//TODO Remove once materials are done
@Deprecated("Temporary")
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class VersatileRegistry(val priority: EventPriority = EventPriority.NORMAL)