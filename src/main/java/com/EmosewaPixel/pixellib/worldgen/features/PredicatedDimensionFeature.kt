package com.EmosewaPixel.pixellib.worldgen.features

import net.minecraft.world.biome.Biome
import net.minecraft.world.dimension.DimensionType
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.IFeatureConfig

import java.util.function.Predicate

//Predicated Dimension Features are Dimension Features that take a Biome Predicate
class PredicatedDimensionFeature<F : IFeatureConfig>(private val pred: Predicate<Biome>, feature: Feature<F>, config: F, dim: DimensionType) : DimensionFeature<F>(feature, config, dim), IPredicatedFeature {
    override fun test(biome: Biome) = pred.test(biome)
}
