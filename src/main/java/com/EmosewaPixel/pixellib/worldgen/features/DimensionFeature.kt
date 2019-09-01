package com.EmosewaPixel.pixellib.worldgen.features

import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorld
import net.minecraft.world.dimension.DimensionType
import net.minecraft.world.gen.ChunkGenerator
import net.minecraft.world.gen.GenerationSettings
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.IFeatureConfig

import java.util.Random

//Dimension Features are Configured Features that only work in a specific Dimension
open class DimensionFeature<F : IFeatureConfig>(featureIn: Feature<F>, featureConfigIn: F, private val dim: DimensionType) : ConfiguredFeature<F>(featureIn, featureConfigIn) {
    override fun place(world: IWorld, generator: ChunkGenerator<out GenerationSettings>, rand: Random, pos: BlockPos) = if (world.dimension.type === dim) super.place(world, generator, rand, pos) else false
}