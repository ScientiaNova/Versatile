package com.EmosewaPixel.pixellib.worldgen.features;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.function.Predicate;

//Predicated Dimension Features are Dimension Features that take a Biome Predicate
public class PredicatedDimensionFeature<F extends IFeatureConfig> extends DimensionFeature<F> implements IPredicatedFeature {
    private Predicate<Biome> pred;

    public PredicatedDimensionFeature(Predicate<Biome> pred, Feature<F> feature, F config, DimensionType dim) {
        super(feature, config, dim);
        this.pred = pred;
    }

    @Override
    public boolean test(Biome biome) {
        return pred.test(biome);
    }
}
