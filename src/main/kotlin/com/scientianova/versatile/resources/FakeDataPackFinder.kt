package com.scientianova.versatile.resources

import net.minecraft.block.Block
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.resources.IPackFinder
import net.minecraft.resources.IResourcePack
import net.minecraft.resources.ResourcePackInfo
import net.minecraft.resources.ResourcePackType
import net.minecraft.tags.Tag
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.ForgeRegistries
import java.util.function.Supplier

object FakeDataPackFinder : IPackFinder {
    override fun <T : ResourcePackInfo> addPackInfosToMap(nameToPackMap: MutableMap<String, T>, packInfoFactory: ResourcePackInfo.IFactory<T>) {
        ResourcePackInfo.createResourcePack("fake_server:versatile", true, Supplier<IResourcePack> {
            val pack = FakePack("fake_server:versatile")

            ITEM_TAGS.asMap().forEach { (name, items) ->
                val tag = Tag.Builder.create<Item>().add(*items.toTypedArray()).build(name)
                pack.putJSON(ResourcePackType.SERVER_DATA, ResourceLocation(name.namespace, "tags/items/${name.path}.json"), tag.serialize { ForgeRegistries.ITEMS.getKey(it) })
            }

            BLOCK_TAGS.asMap().forEach { (name, blocks) ->
                val tag = Tag.Builder.create<Block>().add(*blocks.toTypedArray()).build(name)
                pack.putJSON(ResourcePackType.SERVER_DATA, ResourceLocation(name.namespace, "tags/blocks/${name.path}.json"), tag.serialize { ForgeRegistries.BLOCKS.getKey(it) })
            }

            FLUID_TAGS.asMap().forEach { (name, fluids) ->
                val tag = Tag.Builder.create<Fluid>().add(*fluids.toTypedArray()).build(name)
                pack.putJSON(ResourcePackType.SERVER_DATA, ResourceLocation(name.namespace, "tags/fluids/${name.path}.json"), tag.serialize { ForgeRegistries.FLUIDS.getKey(it) })
            }

            DATA.forEach { (location, jsonElement) -> pack.putJSON(ResourcePackType.SERVER_DATA, location, jsonElement) }

            pack
        }, packInfoFactory, ResourcePackInfo.Priority.BOTTOM)?.let {
            nameToPackMap["fake_server:versatile"] = it
        }
    }
}