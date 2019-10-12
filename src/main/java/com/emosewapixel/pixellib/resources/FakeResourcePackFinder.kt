package com.emosewapixel.pixellib.resources

import net.minecraft.resources.IPackFinder
import net.minecraft.resources.IResourcePack
import net.minecraft.resources.ResourcePackInfo
import net.minecraft.resources.ResourcePackType
import java.util.function.Supplier

//This is used for providing the fake resource pack
object FakeResourcePackFinder : IPackFinder {
    override fun <T : ResourcePackInfo> addPackInfosToMap(nameToPackMap: MutableMap<String, T>, packInfoFactory: ResourcePackInfo.IFactory<T>) {
        val packInfo = ResourcePackInfo.createResourcePack("fake_client:pixellib", true, Supplier<IResourcePack> {
            val pack = FakePack("fake_client:pixellib")

            JSONAdder.ASSETS.forEach { (location, jsonElement) -> pack.putJSON(ResourcePackType.CLIENT_RESOURCES, location, jsonElement) }

            pack
        }, packInfoFactory, ResourcePackInfo.Priority.BOTTOM)
        if (packInfo != null)
            nameToPackMap["fake_client:pixellib"] = packInfo
    }
}