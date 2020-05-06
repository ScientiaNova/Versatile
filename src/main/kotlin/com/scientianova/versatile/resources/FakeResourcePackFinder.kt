package com.scientianova.versatile.resources

import net.minecraft.resources.IPackFinder
import net.minecraft.resources.IResourcePack
import net.minecraft.resources.ResourcePackInfo
import net.minecraft.resources.ResourcePackType
import java.util.function.Supplier

object FakeResourcePackFinder : IPackFinder {
    override fun <T : ResourcePackInfo> addPackInfosToMap(nameToPackMap: MutableMap<String, T>, packInfoFactory: ResourcePackInfo.IFactory<T>) {
        ResourcePackInfo.createResourcePack("fake_client:versatile", true, Supplier<IResourcePack> {
            FakePack("fake_client:versatile").also { pack ->
                ASSETS.forEach { (location, jsonElement) -> pack.putJSON(ResourcePackType.CLIENT_RESOURCES, location, jsonElement) }
            }
        }, packInfoFactory, ResourcePackInfo.Priority.BOTTOM)?.let {
            nameToPackMap["fake_client:versatile"] = it
        }
    }
}