package com.EmosewaPixel.pixellib.worldgen.features;

import com.EmosewaPixel.pixellib.worldgen.features.IPredicatedFeature;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.function.Predicate;

public class PredicatedFeature<F extends IFeatureConfig> extends ConfiguredFeature<F> implements IPredicatedFeature {
    private Predicate<Biome> pred;

    public PredicatedFeature(Predicate<Biome> pred, Feature<F> feature, F config) {
        super(feature, config);
        this.pred = pred;
    }

    @Override
    public boolean test(Biome biome) {
        return pred.test(biome);
    }
}
