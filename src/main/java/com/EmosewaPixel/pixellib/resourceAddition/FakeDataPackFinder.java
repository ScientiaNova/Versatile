package com.EmosewaPixel.pixellib.resourceAddition;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

public class FakeDataPackFinder implements IPackFinder {
    @Override
    public <T extends ResourcePackInfo> void addPackInfosToMap(Map<String, T> nameToPackMap, ResourcePackInfo.IFactory<T> packInfoFactory) {
        T packInfo = ResourcePackInfo.createResourcePack("fake_server:pixellib", true, () -> {
            FakePack pack = new FakePack("fake_server:pixellib");

            TagMaps.ITEM_TAGS.asMap().forEach((name, items) -> {
                Tag<Item> tag = Tag.Builder.<Item>create().addAll(items).build(new ResourceLocation("forge", name));
                pack.putJSON(ResourcePackType.SERVER_DATA, new ResourceLocation("forge", "tags/items/" + name + ".json"), tag.serialize(ForgeRegistries.ITEMS::getKey));
            });
            TagMaps.BLOCK_TAGS.asMap().forEach((name, blocks) -> {
                Tag<Block> tag = Tag.Builder.<Block>create().addAll(blocks).build(new ResourceLocation("forge", name));
                pack.putJSON(ResourcePackType.SERVER_DATA, new ResourceLocation("forge", "tags/blocks/" + name + ".json"), tag.serialize(ForgeRegistries.BLOCKS::getKey));
            });

            JSONAdder.DATA.forEach(((location, jsonElement) -> pack.putJSON(ResourcePackType.SERVER_DATA, location, jsonElement)));

            return pack;
        }, packInfoFactory, ResourcePackInfo.Priority.BOTTOM);
        if (packInfo != null)
            nameToPackMap.put("fake:pixellib", packInfo);
    }
}