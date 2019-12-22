package com.scientianovateam.versatile.resources

import net.minecraft.resources.IPackFinder
import net.minecraft.resources.IResourcePack
import net.minecraft.resources.ResourcePackInfo
import net.minecraft.resources.ResourcePackType
import java.util.function.Supplier

//This is used for providing the fake resource pack
object FakeResourcePackFinder : IPackFinder {
    override fun <T : ResourcePackInfo> addPackInfosToMap(nameToPackMap: MutableMap<String, T>, packInfoFactory: ResourcePackInfo.IFactory<T>) {
        ResourcePackInfo.createResourcePack("fake_client:versatile", true, Supplier<IResourcePack> {
            val pack = FakePack("fake_client:versatile")

            JSONAdder.ASSETS.forEach { (location, jsonElement) -> pack.putJSON(ResourcePackType.CLIENT_RESOURCES, location, jsonElement) }

            pack
        }, packInfoFactory, ResourcePackInfo.Priority.BOTTOM)?.let {
            nameToPackMap["fake_client:versatile"] = it
        }
    }
}