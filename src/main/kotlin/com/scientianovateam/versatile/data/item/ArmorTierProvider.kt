package com.scientianovateam.versatile.data.item

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.data.generation.GSON
import com.scientianovateam.versatile.items.tiers.ArmorTier
import com.scientianovateam.versatile.items.tiers.ArmorTierSerializer
import net.minecraft.data.DataGenerator
import net.minecraft.data.DirectoryCache
import net.minecraft.data.IDataProvider
import java.io.IOException

class ArmorTierProvider(private val dataGenerator: DataGenerator) : IDataProvider {
    private val tiers = mutableListOf<ArmorTier>()

    operator fun ArmorTier.unaryPlus() = this.apply {
        tiers += this
    }

    override fun act(cache: DirectoryCache) {
        tiers.forEach {
            try {
                IDataProvider.save(GSON, cache, ArmorTierSerializer.write(it), dataGenerator.outputFolder.resolve("data/versatile/registries/armor_tiers/${it.registryName}.json"))
            } catch (e: IOException) {
                Versatile.LOGGER.error("Couldn't save armor tier")
            }
        }
    }

    override fun getName() = "Armor Tiers"
}