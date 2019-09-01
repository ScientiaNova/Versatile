package com.EmosewaPixel.pixellib.worldgen.features

import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.IFeatureConfig

import java.util.function.Predicate

//Predicated Features are Configured Features that take a Biome Predicate
class PredicatedFeature<F : IFeatureConfig>(private val pred: Predicate<Biome>, feature: Feature<F>, config: F) : ConfiguredFeature<F>(feature, config), IPredicatedFeature {
    override fun test(biome: Biome) = pred.test(biome)
}
