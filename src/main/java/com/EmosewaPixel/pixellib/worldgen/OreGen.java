package com.EmosewaPixel.pixellib.worldgen;

import com.EmosewaPixel.pixellib.worldgen.features.DimensionFeature;
import com.EmosewaPixel.pixellib.worldgen.features.IPredicatedFeature;
import com.EmosewaPixel.pixellib.worldgen.features.PredicatedDimensionFeature;
import com.EmosewaPixel.pixellib.worldgen.features.PredicatedFeature;
import net.minecraft.block.Block;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/*
To avoid multiple mods adding the same ores, add your ores through here.
Remember to make all world gen configurable.
 */
public class OreGen {
    private static Map<Block, ConfiguredFeature<?>> ORE_MAP = new HashMap<>();

    public static <P extends IPlacementConfig> void addToOreGen(Block block, OreFeatureConfig.FillerBlockType fillerBlockType, int veinSize, Placement<P> placement, P placementConfig) {
        ORE_MAP.put(block, new ConfiguredFeature<>(Feature.DECORATED, new DecoratedFeatureConfig(Feature.ORE, new OreFeatureConfig(fillerBlockType, block.getDefaultState(), veinSize), placement, placementConfig)));
    }

    public static <P extends IPlacementConfig> void addToOreGen(Block block, OreFeatureConfig.FillerBlockType fillerBlockType, int veinSize, Placement<P> placement, P placementConfig, DimensionType dim) {
        ORE_MAP.put(block, new DimensionFeature<>(Feature.DECORATED, new DecoratedFeatureConfig(Feature.ORE, new OreFeatureConfig(fillerBlockType, block.getDefaultState(), veinSize), placement, placementConfig), dim));
    }

    public static <P extends IPlacementConfig> void addToOreGen(Block block, Predicate<Biome> pred, OreFeatureConfig.FillerBlockType fillerBlockType, int veinSize, Placement<P> placement, P placementConfig) {
        ORE_MAP.put(block, new PredicatedFeature<>(pred, Feature.DECORATED, new DecoratedFeatureConfig(Feature.ORE, new OreFeatureConfig(fillerBlockType, block.getDefaultState(), veinSize), placement, placementConfig)));
    }

    public static <P extends IPlacementConfig> void addToOreGen(Block block, Predicate<Biome> pred, OreFeatureConfig.FillerBlockType fillerBlockType, int veinSize, Placement<P> placement, P placementConfig, DimensionType dim) {
        ORE_MAP.put(block, new PredicatedDimensionFeature<>(pred, Feature.DECORATED, new DecoratedFeatureConfig(Feature.ORE, new OreFeatureConfig(fillerBlockType, block.getDefaultState(), veinSize), placement, placementConfig), dim));
    }

    public static void register() {
        ForgeRegistries.BIOMES.forEach(biome -> ORE_MAP.values().forEach(ore -> {
            if (ore instanceof IPredicatedFeature) {
                if (((IPredicatedFeature) ore).test(biome))
                    biome.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, ore);
            } else
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, ore);
        }));
    }
}
