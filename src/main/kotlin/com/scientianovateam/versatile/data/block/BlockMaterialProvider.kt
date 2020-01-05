package com.scientianovateam.versatile.data.block

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.blocks.properties.BlockMaterialWrapper
import com.scientianovateam.versatile.data.GSON
import net.minecraft.data.DataGenerator
import net.minecraft.data.DirectoryCache
import net.minecraft.data.IDataProvider
import java.io.IOException

class BlockMaterialProvider(private val dataGenerator: DataGenerator) : IDataProvider {
    private val tiers = mutableListOf<BlockMaterialWrapper>()

    operator fun BlockMaterialWrapper.unaryPlus() = this.let {
        tiers += it
    }

    override fun act(cache: DirectoryCache) {
        tiers.forEach {
            try {
                IDataProvider.save(GSON, cache, BlockMaterialWrapper.Serializer.write(it), dataGenerator.outputFolder.resolve("data/${it.registryName.namespace}/registries/block_materials/${it.registryName.path}.json"))
            } catch (e: IOException) {
                Versatile.LOGGER.error("Couldn't save block material")
            }
        }
    }

    override fun getName() = "Block Materials"
}