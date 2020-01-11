package com.scientianovateam.versatile.data.material

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.data.generation.GSON
import com.scientianovateam.versatile.materialsystem.elements.Element
import com.scientianovateam.versatile.materialsystem.elements.ElementSerializer
import net.minecraft.data.DataGenerator
import net.minecraft.data.DirectoryCache
import net.minecraft.data.IDataProvider
import java.io.IOException

class ElementProvider(private val dataGenerator: DataGenerator) : IDataProvider {
    private val elements = mutableListOf<Element>()

    operator fun Element.unaryPlus() = this.apply {
        elements += this
    }

    override fun act(cache: DirectoryCache) {
        elements.forEach {
            try {
                IDataProvider.save(GSON, cache, ElementSerializer.write(it), dataGenerator.outputFolder.resolve("data/versatile/registries/elements/${it.name}.json"))
            } catch (e: IOException) {
                Versatile.LOGGER.error("Couldn't save element")
            }
        }
    }

    override fun getName() = "Elements"
}