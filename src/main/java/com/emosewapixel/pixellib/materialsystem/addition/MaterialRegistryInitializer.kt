package com.emosewapixel.pixellib.materialsystem.addition

import com.emosewapixel.pixellib.PixelLib
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.loading.moddiscovery.ModAnnotation
import net.minecraftforge.forgespi.language.ModFileScanData
import org.objectweb.asm.Type

object MaterialRegistryInitializer {
    val instances = mutableListOf<Any>()

    init {
        val type = Type.getType(MaterialRegistryClass::class.java)
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