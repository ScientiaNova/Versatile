package com.scientianovateam.versatile.data.block

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.blocks.properties.SoundTypeV
import com.scientianovateam.versatile.data.GSON
import net.minecraft.data.DataGenerator
import net.minecraft.data.DirectoryCache
import net.minecraft.data.IDataProvider
import java.io.IOException

class SoundTypeProvider(private val dataGenerator: DataGenerator) : IDataProvider {
    private val tiers = mutableListOf<SoundTypeV>()

    operator fun SoundTypeV.unaryPlus() = this.let {
        tiers += it
    }

    override fun act(cache: DirectoryCache) {
        tiers.forEach {
            try {
                IDataProvider.save(GSON, cache, SoundTypeV.Serializer.write(it), dataGenerator.outputFolder.resolve("data/${it.registryName.namespace}/registries/sound_types/${it.registryName.path}.json"))
            } catch (e: IOException) {
                Versatile.LOGGER.error("Couldn't save sound type")
            }
        }
    }

    override fun getName() = "Sound Types"
}