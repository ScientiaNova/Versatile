package com.scientianovateam.versatile.data.block

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.blocks.serializable.ISerializableBlock
import com.scientianovateam.versatile.blocks.serializable.serialize
import com.scientianovateam.versatile.data.generation.GSON
import net.minecraft.data.DataGenerator
import net.minecraft.data.DirectoryCache
import net.minecraft.data.IDataProvider
import java.io.IOException

class BlockProvider(private val dataGenerator: DataGenerator) : IDataProvider {
    private val blocks = mutableListOf<ISerializableBlock>()

    operator fun <T> T.unaryPlus() where T : ISerializableBlock = this.apply {
        blocks += this
    }

    override fun act(cache: DirectoryCache) {
        blocks.forEach {
            try {
                IDataProvider.save(GSON, cache, it.serialize(), dataGenerator.outputFolder.resolve("data/versatile/${it.registryName!!.namespace}/blocks/${it.registryName!!.path}.json"))
            } catch (e: IOException) {
                Versatile.LOGGER.error("Couldn't save block")
            }
        }
    }

    override fun getName() = "Blocks"
}