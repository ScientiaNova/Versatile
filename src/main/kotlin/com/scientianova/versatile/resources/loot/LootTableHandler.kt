package com.scientianova.versatile.resources.loot

import net.minecraft.util.ResourceLocation
import net.minecraft.world.storage.loot.LootTable

class LootTableHandler(private val map: MutableMap<ResourceLocation, LootTable>) {
    fun add(name: ResourceLocation, table: LootTable) {
        map[name] = table
    }

    fun remove(name: ResourceLocation) = map.remove(name)

    fun get(name: ResourceLocation) = map[name]
}