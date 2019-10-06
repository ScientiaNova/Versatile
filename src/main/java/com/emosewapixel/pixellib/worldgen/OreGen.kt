package com.emosewapixel.pixellib.worldgen

import com.emosewapixel.pixellib.worldgen.features.DimensionFeature
import com.emosewapixel.pixellib.worldgen.features.IPredicatedFeature
import com.emosewapixel.pixellib.worldgen.features.PredicatedDimensionFeature
import com.emosewapixel.pixellib.worldgen.features.PredicatedFeature
import net.minecraft.block.Block
import net.minecraft.world.biome.Biome
import net.minecraft.world.dimension.DimensionType
import net.minecraft.world.gen.GenerationStage
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.DecoratedFeatureConfig
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.OreFeatureConfig
import net.minecraft.world.gen.placement.IPlacementConfig
import net.minecraft.world.gen.placement.Placement
import net.minecraftforge.registries.ForgeRegistries
import java.util.*

/*
This class contains methods for adding ore gen.
To avoid multiple mods adding the same ores, it's bs to add your ores through here.
Remember to make all world gen configurable.
 */
object OreGen {
    private val ORE_MAP = HashMap<Block, ConfiguredFeature<*>>()

    fun <P : IPlacementConfig> addToOreGen(block: Block, fillerBlockType: OreFeatureConfig.FillerBlockType, veinSize: Int, placement: Placement<P>, placementConfig: P) {
        ORE_MAP[block] = ConfiguredFeature(Feature.DECORATED, DecoratedFeatureConfig(Feature.ORE, OreFeatureConfig(fillerBlockType, block.defaultState, veinSize), placement, placementConfig))
    }

    fun <P : IPlacementConfig> addToOreGen(block: Block, fillerBlockType: OreFeatureConfig.FillerBlockType, veinSize: Int, placement: Placement<P>, placementConfig: P, dim: DimensionType) {
        ORE_MAP[block] = DimensionFeature(Feature.DECORATED, DecoratedFeatureConfig(Feature.ORE, OreFeatureConfig(fillerBlockType, block.defaultState, veinSize), placement, placementConfig), dim)
    }

    fun <P : IPlacementConfig> addToOreGen(block: Block, pred: (Biome) -> Boolean, fillerBlockType: OreFeatureConfig.FillerBlockType, veinSize: Int, placement: Placement<P>, placementConfig: P) {
        ORE_MAP[block] = PredicatedFeature(pred, Feature.DECORATED, DecoratedFeatureConfig(Feature.ORE, OreFeatureConfig(fillerBlockType, block.defaultState, veinSize), placement, placementConfig))
    }

    fun <P : IPlacementConfig> addToOreGen(block: Block, pred: (Biome) -> Boolean, fillerBlockType: OreFeatureConfig.FillerBlockType, veinSize: Int, placement: Placement<P>, placementConfig: P, dim: DimensionType) {
        ORE_MAP[block] = PredicatedDimensionFeature(pred, Feature.DECORATED, DecoratedFeatureConfig(Feature.ORE, OreFeatureConfig(fillerBlockType, block.defaultState, veinSize), placement, placementConfig), dim)
    }

    fun register() {
        ForgeRegistries.BIOMES.forEach { biome ->
            ORE_MAP.values.forEach { ore ->
                if (ore is IPredicatedFeature) {
                    if ((ore as IPredicatedFeature).test(biome))
                        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, ore)
                } else
                    biome.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, ore)
            }
        }
    }
}