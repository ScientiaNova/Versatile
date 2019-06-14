package com.EmosewaPixel.pixellib.resourceAddition;

import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackType;

import java.util.Map;

public class FakeResourcePackFinder implements IPackFinder {
    @Override
    public <T extends ResourcePackInfo> void addPackInfosToMap(Map<String, T> nameToPackMap, ResourcePackInfo.IFactory<T> packInfoFactory) {
        T packInfo = ResourcePackInfo.createResourcePack("fake_client:pixellib", true, () -> {
            FakePack pack = new FakePack("fake_client:pixellib");

            JSONAdder.ASSETS.forEach(((location, jsonElement) -> pack.putJSON(ResourcePackType.CLIENT_RESOURCES, location, jsonElement)));

            return pack;
        }, packInfoFactory, ResourcePackInfo.Priority.BOTTOM);
        if (packInfo != null)
            nameToPackMap.put("fake:pixellib", packInfo);
    }
}