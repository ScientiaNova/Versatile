package com.emosewapixel.pixellib.worldgen.features

import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.IFeatureConfig

//Predicated Features are Configured Features that take a Biome Predicate
open class PredicatedFeature<F : IFeatureConfig>(private val pred: (Biome) -> Boolean, feature: Feature<F>, config: F) : ConfiguredFeature<F>(feature, config), IPredicatedFeature {
    override fun test(biome: Biome) = pred(biome)
}