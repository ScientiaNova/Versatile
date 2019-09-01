package com.EmosewaPixel.pixellib.resources

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.resources.IPackFinder
import net.minecraft.resources.IResourcePack
import net.minecraft.resources.ResourcePackInfo
import net.minecraft.resources.ResourcePackType
import net.minecraft.tags.Tag
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.ForgeRegistries
import java.util.function.Supplier

//This is used for providing the fake data pack
class FakeDataPackFinder : IPackFinder {
    override fun <T : ResourcePackInfo> addPackInfosToMap(nameToPackMap: MutableMap<String, T>, packInfoFactory: ResourcePackInfo.IFactory<T>) {
        val packInfo = ResourcePackInfo.createResourcePack("fake_server:pixellib", true, Supplier<IResourcePack> {
            val pack = FakePack("fake_server:pixellib")

            TagMaps.ITEM_TAGS.asMap().forEach { (name, items) ->
                val tag = Tag.Builder.create<Item>().add(*items.toTypedArray()).build(ResourceLocation("forge", name))
                pack.putJSON(ResourcePackType.SERVER_DATA, ResourceLocation("forge", "tags/items/$name.json"), tag.serialize { ForgeRegistries.ITEMS.getKey(it) })
            }

            TagMaps.BLOCK_TAGS.asMap().forEach { (name, blocks) ->
                val tag = Tag.Builder.create<Block>().add(*blocks.toTypedArray()).build(ResourceLocation("forge", name))
                pack.putJSON(ResourcePackType.SERVER_DATA, ResourceLocation("forge", "tags/blocks/$name.json"), tag.serialize { ForgeRegistries.BLOCKS.getKey(it) })
            }

            JSONAdder.DATA.forEach { (location, jsonElement) -> pack.putJSON(ResourcePackType.SERVER_DATA, location, jsonElement) }

            pack
        }, packInfoFactory, ResourcePackInfo.Priority.BOTTOM)
        if (packInfo != null)
            nameToPackMap["fake_server:pixellib"] = packInfo
    }
}