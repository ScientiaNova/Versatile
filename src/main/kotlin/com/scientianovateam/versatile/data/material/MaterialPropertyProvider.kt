package com.scientianovateam.versatile.data.material

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.materialsystem.serializers.PropertySerializer
import com.scientianovateam.versatile.data.generation.GSON
import com.scientianovateam.versatile.materialsystem.properties.Property
import net.minecraft.data.DataGenerator
import net.minecraft.data.DirectoryCache
import net.minecraft.data.IDataProvider
import java.io.IOException

class MaterialPropertyProvider(private val dataGenerator: DataGenerator) : IDataProvider {
    private val properties = mutableListOf<Property>()

    operator fun Property.unaryPlus() = this.apply {
        properties += this
    }

    override fun act(cache: DirectoryCache) {
        properties.forEach {
            try {
                IDataProvider.save(GSON, cache, PropertySerializer.write(it), dataGenerator.outputFolder.resolve("data/${it.name.namespace}/registries/mat_properties/${it.name.path}.json"))
            } catch (e: IOException) {
                Versatile.LOGGER.error("Couldn't save material property")
            }
        }
    }

    override fun getName() = "Material Properties"
}