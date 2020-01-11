package com.scientianovateam.versatile.data.generation

import com.scientianovateam.versatile.blocks.properties.SoundTypeV
import com.scientianovateam.versatile.common.extensions.toResLoc
import net.minecraft.data.DataGenerator
import net.minecraft.util.SoundEvents

fun DataGenerator.addSoundTypes() = soundTypes {
    +SoundTypeV(
            registryName = "wood".toResLoc(),
            breakSoundSupplier = { SoundEvents.BLOCK_WOOD_BREAK },
            stepSoundSupplier = { SoundEvents.BLOCK_WOOD_STEP },
            placeSoundSupplier = { SoundEvents.BLOCK_WOOD_PLACE },
            hitSoundSupplier = { SoundEvents.BLOCK_WOOD_HIT },
            fallSoundSupplier = { SoundEvents.BLOCK_WOOD_FALL }
    )
    +SoundTypeV(
            registryName = "ground".toResLoc(),
            breakSoundSupplier = { SoundEvents.BLOCK_GRAVEL_BREAK },
            stepSoundSupplier = { SoundEvents.BLOCK_GRAVEL_STEP },
            placeSoundSupplier = { SoundEvents.BLOCK_GRAVEL_PLACE },
            hitSoundSupplier = { SoundEvents.BLOCK_GRAVEL_HIT },
            fallSoundSupplier = { SoundEvents.BLOCK_GRAVEL_FALL }
    )
    +SoundTypeV(
            registryName = "plant".toResLoc(),
            breakSoundSupplier = { SoundEvents.BLOCK_GRASS_BREAK },
            stepSoundSupplier = { SoundEvents.BLOCK_GRASS_STEP },
            placeSoundSupplier = { SoundEvents.BLOCK_GRASS_PLACE },
            hitSoundSupplier = { SoundEvents.BLOCK_GRASS_HIT },
            fallSoundSupplier = { SoundEvents.BLOCK_GRASS_FALL }
    )
    +SoundTypeV(
            registryName = "stone".toResLoc(),
            breakSoundSupplier = { SoundEvents.BLOCK_STONE_BREAK },
            stepSoundSupplier = { SoundEvents.BLOCK_STONE_STEP },
            placeSoundSupplier = { SoundEvents.BLOCK_STONE_PLACE },
            hitSoundSupplier = { SoundEvents.BLOCK_STONE_HIT },
            fallSoundSupplier = { SoundEvents.BLOCK_STONE_FALL }
    )
    +SoundTypeV(
            registryName = "metal".toResLoc(),
            pitch = 1.5f,
            breakSoundSupplier = { SoundEvents.BLOCK_METAL_BREAK },
            stepSoundSupplier = { SoundEvents.BLOCK_METAL_STEP },
            placeSoundSupplier = { SoundEvents.BLOCK_METAL_PLACE },
            hitSoundSupplier = { SoundEvents.BLOCK_METAL_HIT },
            fallSoundSupplier = { SoundEvents.BLOCK_METAL_FALL }
    )
    +SoundTypeV(
            registryName = "glass".toResLoc(),
            breakSoundSupplier = { SoundEvents.BLOCK_GLASS_BREAK },
            stepSoundSupplier = { SoundEvents.BLOCK_GLASS_STEP },
            placeSoundSupplier = { SoundEvents.BLOCK_GLASS_PLACE },
            hitSoundSupplier = { SoundEvents.BLOCK_GLASS_HIT },
            fallSoundSupplier = { SoundEvents.BLOCK_GLASS_FALL }
    )
    +SoundTypeV(
            registryName = "cloth".toResLoc(),
            breakSoundSupplier = { SoundEvents.BLOCK_WOOL_BREAK },
            stepSoundSupplier = { SoundEvents.BLOCK_WOOL_STEP },
            placeSoundSupplier = { SoundEvents.BLOCK_WOOL_PLACE },
            hitSoundSupplier = { SoundEvents.BLOCK_WOOL_HIT },
            fallSoundSupplier = { SoundEvents.BLOCK_WOOL_FALL }
    )
    +SoundTypeV(
            registryName = "sand".toResLoc(),
            breakSoundSupplier = { SoundEvents.BLOCK_SAND_BREAK },
            stepSoundSupplier = { SoundEvents.BLOCK_SAND_STEP },
            placeSoundSupplier = { SoundEvents.BLOCK_SAND_PLACE },
            hitSoundSupplier = { SoundEvents.BLOCK_SAND_HIT },
            fallSoundSupplier = { SoundEvents.BLOCK_SAND_FALL }
    )
    +SoundTypeV(
            registryName = "snow".toResLoc(),
            breakSoundSupplier = { SoundEvents.BLOCK_SNOW_BREAK },
            stepSoundSupplier = { SoundEvents.BLOCK_SNOW_STEP },
            placeSoundSupplier = { SoundEvents.BLOCK_SNOW_PLACE },
            hitSoundSupplier = { SoundEvents.BLOCK_SNOW_HIT },
            fallSoundSupplier = { SoundEvents.BLOCK_SNOW_FALL }
    )
    +SoundTypeV(
            registryName = "ladder".toResLoc(),
            breakSoundSupplier = { SoundEvents.BLOCK_LADDER_BREAK },
            stepSoundSupplier = { SoundEvents.BLOCK_LADDER_STEP },
            placeSoundSupplier = { SoundEvents.BLOCK_LADDER_PLACE },
            hitSoundSupplier = { SoundEvents.BLOCK_LADDER_HIT },
            fallSoundSupplier = { SoundEvents.BLOCK_LADDER_FALL }
    )
    +SoundTypeV(
            registryName = "anvil".toResLoc(),
            volume = 0.3f,
            breakSoundSupplier = { SoundEvents.BLOCK_ANVIL_BREAK },
            stepSoundSupplier = { SoundEvents.BLOCK_ANVIL_STEP },
            placeSoundSupplier = { SoundEvents.BLOCK_ANVIL_PLACE },
            hitSoundSupplier = { SoundEvents.BLOCK_ANVIL_HIT },
            fallSoundSupplier = { SoundEvents.BLOCK_ANVIL_FALL }
    )
    +SoundTypeV(
            registryName = "slime".toResLoc(),
            breakSoundSupplier = { SoundEvents.BLOCK_SLIME_BLOCK_BREAK },
            stepSoundSupplier = { SoundEvents.BLOCK_SLIME_BLOCK_STEP },
            placeSoundSupplier = { SoundEvents.BLOCK_SLIME_BLOCK_PLACE },
            hitSoundSupplier = { SoundEvents.BLOCK_SLIME_BLOCK_HIT },
            fallSoundSupplier = { SoundEvents.BLOCK_SLIME_BLOCK_FALL }
    )
    +SoundTypeV(
            registryName = "wet_grass".toResLoc(),
            breakSoundSupplier = { SoundEvents.BLOCK_WET_GRASS_BREAK },
            stepSoundSupplier = { SoundEvents.BLOCK_WET_GRASS_STEP },
            placeSoundSupplier = { SoundEvents.BLOCK_WET_GRASS_PLACE },
            hitSoundSupplier = { SoundEvents.BLOCK_WET_GRASS_HIT },
            fallSoundSupplier = { SoundEvents.BLOCK_WET_GRASS_FALL }
    )
    +SoundTypeV(
            registryName = "coral".toResLoc(),
            breakSoundSupplier = { SoundEvents.BLOCK_CORAL_BLOCK_BREAK },
            stepSoundSupplier = { SoundEvents.BLOCK_CORAL_BLOCK_STEP },
            placeSoundSupplier = { SoundEvents.BLOCK_CORAL_BLOCK_PLACE },
            hitSoundSupplier = { SoundEvents.BLOCK_CORAL_BLOCK_HIT },
            fallSoundSupplier = { SoundEvents.BLOCK_CORAL_BLOCK_FALL }
    )
    +SoundTypeV(
            registryName = "bamboo".toResLoc(),
            breakSoundSupplier = { SoundEvents.BLOCK_BAMBOO_BREAK },
            stepSoundSupplier = { SoundEvents.BLOCK_BAMBOO_STEP },
            placeSoundSupplier = { SoundEvents.BLOCK_BAMBOO_PLACE },
            hitSoundSupplier = { SoundEvents.BLOCK_BAMBOO_HIT },
            fallSoundSupplier = { SoundEvents.BLOCK_BAMBOO_FALL }
    )
    +SoundTypeV(
            registryName = "bamboo_sapling".toResLoc(),
            breakSoundSupplier = { SoundEvents.BLOCK_BAMBOO_SAPLING_BREAK },
            stepSoundSupplier = { SoundEvents.BLOCK_BAMBOO_STEP },
            placeSoundSupplier = { SoundEvents.BLOCK_BAMBOO_SAPLING_PLACE },
            hitSoundSupplier = { SoundEvents.BLOCK_BAMBOO_SAPLING_HIT },
            fallSoundSupplier = { SoundEvents.BLOCK_BAMBOO_FALL }
    )
    +SoundTypeV(
            registryName = "scaffolding".toResLoc(),
            breakSoundSupplier = { SoundEvents.BLOCK_SCAFFOLDING_BREAK },
            stepSoundSupplier = { SoundEvents.BLOCK_SCAFFOLDING_STEP },
            placeSoundSupplier = { SoundEvents.BLOCK_SCAFFOLDING_PLACE },
            hitSoundSupplier = { SoundEvents.BLOCK_SCAFFOLDING_HIT },
            fallSoundSupplier = { SoundEvents.BLOCK_SCAFFOLDING_FALL }
    )
    +SoundTypeV(
            registryName = "sweet_berry_bush".toResLoc(),
            breakSoundSupplier = { SoundEvents.BLOCK_SWEET_BERRY_BUSH_BREAK },
            stepSoundSupplier = { SoundEvents.BLOCK_GRASS_STEP },
            placeSoundSupplier = { SoundEvents.BLOCK_SWEET_BERRY_BUSH_PLACE },
            hitSoundSupplier = { SoundEvents.BLOCK_GRASS_HIT },
            fallSoundSupplier = { SoundEvents.BLOCK_GRASS_FALL }
    )
    +SoundTypeV(
            registryName = "crop".toResLoc(),
            breakSoundSupplier = { SoundEvents.BLOCK_CROP_BREAK },
            stepSoundSupplier = { SoundEvents.BLOCK_GRASS_STEP },
            placeSoundSupplier = { SoundEvents.ITEM_CROP_PLANT },
            hitSoundSupplier = { SoundEvents.BLOCK_GRASS_HIT },
            fallSoundSupplier = { SoundEvents.BLOCK_GRASS_FALL }
    )
    +SoundTypeV(
            registryName = "stem".toResLoc(),
            breakSoundSupplier = { SoundEvents.BLOCK_WOOD_BREAK },
            stepSoundSupplier = { SoundEvents.BLOCK_WOOD_STEP },
            placeSoundSupplier = { SoundEvents.ITEM_CROP_PLANT },
            hitSoundSupplier = { SoundEvents.BLOCK_WOOD_HIT },
            fallSoundSupplier = { SoundEvents.BLOCK_WOOD_FALL }
    )
    +SoundTypeV(
            registryName = "nether_wart".toResLoc(),
            breakSoundSupplier = { SoundEvents.BLOCK_NETHER_WART_BREAK },
            stepSoundSupplier = { SoundEvents.BLOCK_STONE_STEP },
            placeSoundSupplier = { SoundEvents.ITEM_NETHER_WART_PLANT },
            hitSoundSupplier = { SoundEvents.BLOCK_STONE_HIT },
            fallSoundSupplier = { SoundEvents.BLOCK_STONE_FALL }
    )
    +SoundTypeV(
            registryName = "lantern".toResLoc(),
            breakSoundSupplier = { SoundEvents.BLOCK_LANTERN_BREAK },
            stepSoundSupplier = { SoundEvents.BLOCK_LANTERN_STEP },
            placeSoundSupplier = { SoundEvents.BLOCK_LANTERN_PLACE },
            hitSoundSupplier = { SoundEvents.BLOCK_LANTERN_HIT },
            fallSoundSupplier = { SoundEvents.BLOCK_LANTERN_FALL }
    )
}