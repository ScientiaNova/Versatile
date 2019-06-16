package com.EmosewaPixel.pixellib.worldgen.features;

import net.minecraft.world.biome.Biome;

import java.util.function.Predicate;

/*
This is an interface used for adding Biome Predicates to Configured Features.
They're mainly used for determining which biomes the feature needs to be added to in OreGen
*/
public interface IPredicatedFeature extends Predicate<Biome> {
}
