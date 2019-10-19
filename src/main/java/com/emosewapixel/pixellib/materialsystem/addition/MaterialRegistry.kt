package com.emosewapixel.pixellib.materialsystem.addition

import net.minecraftforge.eventbus.api.EventPriority

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class MaterialRegistry(val priority: EventPriority = EventPriority.NORMAL)