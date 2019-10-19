package com.emosewapixel.pixellib.materialsystem.addition

import com.emosewapixel.pixellib.PixelLib
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.loading.moddiscovery.ModAnnotation
import net.minecraftforge.forgespi.language.ModFileScanData
import org.objectweb.asm.Type

abstract class AnnotationInitializer(annotation: Class<*>) {
    val instances = mutableListOf<Any>()

    val type = Type.getType(annotation)

    init {
        ModList.get().allScanData.flatMap(ModFileScanData::getAnnotations).filter { it.annotationType == type }
                .groupBy {
                    EventPriority.valueOf((it.annotationData["priority"] as? ModAnnotation.EnumHolder)?.value
                            ?: "NORMAL")
                }
                .toSortedMap().flatMap { (_, v) -> v.map(ModFileScanData.AnnotationData::getMemberName) }.forEach {
                    try {
                        val clazz = Class.forName(it)
                        instances += clazz.kotlin.objectInstance ?: clazz.newInstance()
                    } catch (e: Exception) {
                        PixelLib.LOGGER.error("Couldn't initialize material registry class $it", e)
                    }
                }
    }
}