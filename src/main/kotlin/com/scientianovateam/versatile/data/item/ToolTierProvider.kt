package com.scientianovateam.versatile.data.item

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.data.GSON
import com.scientianovateam.versatile.items.tiers.ToolTier
import com.scientianovateam.versatile.items.tiers.ToolTierSerializer
import net.minecraft.data.DataGenerator
import net.minecraft.data.DirectoryCache
import net.minecraft.data.IDataProvider
import java.io.IOException

class ToolTierProvider(private val dataGenerator: DataGenerator) : IDataProvider {
    private val tiers = mutableListOf<ToolTier>()

    operator fun ToolTier.unaryPlus() = this.let {
        tiers += it
    }

    override fun act(cache: DirectoryCache) {
        tiers.forEach {
            try {
                IDataProvider.save(GSON, cache, ToolTierSerializer.write(it), dataGenerator.outputFolder.resolve("data/versatile/registries/tool_tiers/${it.registryName}.json"))
            } catch (e: IOException) {
                Versatile.LOGGER.error("Couldn't save tool tier")
            }
        }
    }

    override fun getName() = "Tool Tiers"
}