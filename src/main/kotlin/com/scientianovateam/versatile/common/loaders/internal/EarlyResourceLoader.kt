package com.scientianovateam.versatile.common.loaders.internal

import com.google.gson.JsonObject
import net.minecraft.resources.FallbackResourceManager
import net.minecraft.resources.IResource
import net.minecraft.util.JSONUtils
import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo
import net.minecraftforge.fml.packs.ModFileResourcePack
import java.io.InputStreamReader

val earlyResources by lazy { EarlyGameResourceLoader() }

class EarlyGameResourceLoader {
    private val packs = ModList.get().modFiles.filter { it.modLoader != "minecraft" }.map { mf: ModFileInfo -> ModFileResourcePack(mf.file) }
    private val manager: FallbackResourceManager = TODO("1.15")

    init {
        packs.forEach { manager.addResourcePack(it) }
    }

    fun resources(path: String): List<List<IResource>> {
        return manager.getAllResourceLocations(path) { it.endsWith(".json") }.map(manager::getAllResources)
    }

    fun loadJsons(path: String): List<JsonObject> = resources(path).flatMap { set ->
        set.map {
            it.use { resource ->
                JSONUtils.fromJson(InputStreamReader(resource.inputStream)).apply {
                    addProperty("namespace", resource.location.namespace)
                }
            }
        }
    }


    companion object {
        private const val jsonExtensionLength = ".json".length
    }
}