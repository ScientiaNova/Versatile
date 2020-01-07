package com.scientianovateam.versatile.data.material

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.common.serialization.registries.PropertySerializer
import com.scientianovateam.versatile.data.GSON
import com.scientianovateam.versatile.materialsystem.properties.Property
import net.minecraft.data.DataGenerator
import net.minecraft.data.DirectoryCache
import net.minecraft.data.IDataProvider
import java.io.IOException

class MaterialPropertyProvider(private val dataGenerator: DataGenerator) : IDataProvider {
    private val tiers = mutableListOf<Property>()

    operator fun Property.unaryPlus() = this.let {
        tiers += it
    }

    override fun act(cache: DirectoryCache) {
        tiers.forEach {
            try {
                IDataProvider.save(GSON, cache, PropertySerializer.write(it), dataGenerator.outputFolder.resolve("data/${it.name.namespace}/registries/mat_properties/${it.name.path}.json"))
            } catch (e: IOException) {
                Versatile.LOGGER.error("Couldn't save material property")
            }
        }
    }

    override fun getName() = "Material Properties"
}