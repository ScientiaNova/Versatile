package com.scientianovateam.versatile.data.material

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.materialsystem.serializers.MaterialSerializer
import com.scientianovateam.versatile.data.generation.GSON
import com.scientianovateam.versatile.materialsystem.main.Material
import net.minecraft.data.DataGenerator
import net.minecraft.data.DirectoryCache
import net.minecraft.data.IDataProvider
import java.io.IOException

class MaterialProvider(private val dataGenerator: DataGenerator) : IDataProvider {
    private val materials = mutableListOf<Material>()

    operator fun Material.unaryPlus() = this.apply {
        materials += this
    }

    override fun act(cache: DirectoryCache) {
        materials.forEach {
            try {
                IDataProvider.save(GSON, cache, MaterialSerializer.write(it), dataGenerator.outputFolder.resolve("data/versatile/registries/materials/${it.name}.json"))
            } catch (e: IOException) {
                Versatile.LOGGER.error("Couldn't save material")
            }
        }
    }

    override fun getName() = "Materials"
}