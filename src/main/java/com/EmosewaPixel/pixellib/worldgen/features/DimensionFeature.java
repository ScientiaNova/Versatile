package com.EmosewaPixel.pixellib.worldgen.features;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.Random;

public class DimensionFeature<F extends IFeatureConfig> extends ConfiguredFeature<F> {
    private DimensionType dim;

    public DimensionFeature(Feature<F> featureIn, F featureConfigIn, DimensionType dim) {
        super(featureIn, featureConfigIn);
        this.dim = dim;
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos) {
        if (world.getDimension().getType() == dim)
            return super.place(world, generator, rand, pos);
        return false;
    }
}