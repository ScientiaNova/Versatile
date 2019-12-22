package com.scientianovateam.versatile.common.registry

import com.scientianovateam.versatile.Versatile
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.loading.moddiscovery.ModAnnotation
import net.minecraftforge.forgespi.language.ModFileScanData
import org.objectweb.asm.Type

abstract class AnnotationInitializer(annotation: Class<*>) {
    val instances: List<Any>

    val type: Type = Type.getType(annotation)

    init {
        instances = ModList.get().allScanData.flatMap(ModFileScanData::getAnnotations).filter { it.annotationType == type }
                .groupBy {
                    EventPriority.valueOf((it.annotationData["priority"] as? ModAnnotation.EnumHolder)?.value
                            ?: "NORMAL")
                }
                .toSortedMap().flatMap { (_, v) -> v.map(ModFileScanData.AnnotationData::getMemberName) }.mapNotNull {
                    try {
                        val clazz = Class.forName(it)
                        clazz.kotlin.objectInstance ?: clazz.newInstance()
                    } catch (e: Exception) {
                        Versatile.LOGGER.error("Couldn't initialize material registry class $it", e)
                    }
                }
    }
}