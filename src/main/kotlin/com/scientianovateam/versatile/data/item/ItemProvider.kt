package com.scientianovateam.versatile.data.item

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.data.GSON
import com.scientianovateam.versatile.items.serializable.ISerializableItem
import com.scientianovateam.versatile.items.serializable.serialize
import net.minecraft.data.DataGenerator
import net.minecraft.data.DirectoryCache
import net.minecraft.data.IDataProvider
import java.io.IOException

class ItemProvider(private val dataGenerator: DataGenerator) : IDataProvider {
    private val tiers = mutableListOf<ISerializableItem>()

    operator fun <T> T.unaryPlus() where T : ISerializableItem = this.let {
        tiers += it
    }

    override fun act(cache: DirectoryCache) {
        tiers.forEach {
            try {
                IDataProvider.save(GSON, cache, it.serialize(), dataGenerator.outputFolder.resolve("data/${it.registryName!!.namespace}/registries/items/${it.registryName!!.path}.json"))
            } catch (e: IOException) {
                Versatile.LOGGER.error("Couldn't save item")
            }
        }
    }

    override fun getName() = "Items"
}