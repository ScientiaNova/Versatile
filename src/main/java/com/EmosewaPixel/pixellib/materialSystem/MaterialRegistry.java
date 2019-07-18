package com.EmosewaPixel.pixellib.materialsystem;

import com.EmosewaPixel.pixellib.materialsystem.element.Elements;
import com.EmosewaPixel.pixellib.materialsystem.lists.MaterialBlocks;
import com.EmosewaPixel.pixellib.materialsystem.lists.MaterialItems;
import com.EmosewaPixel.pixellib.materialsystem.materials.*;
import com.EmosewaPixel.pixellib.materialsystem.types.BlockType;
import com.EmosewaPixel.pixellib.materialsystem.types.ItemType;
import com.EmosewaPixel.pixellib.materialsystem.types.ObjectType;
import com.EmosewaPixel.pixellib.materialsystem.types.TextureType;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;

//This class is used for registering the vanilla material and object type tags, materials and object types
public class MaterialRegistry {
    public static TextureType ROUGH, REGULAR, SHINY, FUEL, PENTAGONAL, OCTAGONAL, CRYSTAL, SHARP, FINE, TRANSPARENT_FLUID, OPAQUE_FLUID;

    public static Material IRON, GOLD, LAPIS, QUARTZ, DIAMOND, EMERALD, REDSTONE, STONE, WOODEN, COAL, GLOWSTONE, BRICK, FLINT, CHARCOAL, NETHER_BRICK, WATER, LAVA, BONE, BLAZE, OBSIDIAN;

    public static ObjectType DUST, INGOT, NUGGET, BLOCK, ORE, GEM;

    public static final String HAS_ORE = "has_ore";
    public static final String DISABLE_SIMPLE_PROCESSING = "disable_simple_processing";
    public static final String BLOCK_FROM_4X4 = "block_from_4x4";
    public static final String IS_GAS = "is_gas";
    public static final String HAS_NO_FUEL_VALUE = "has_no_fuel_value";
    public static final String ROD_TO_3_DUST = "rod_to_3_dust";

    public static final String SINGLE_TEXTURE_TYPE = "1_texture_type";
    public static final String USES_UNREFINED_COLOR = "uses_unrefined_color";

    static {
        //Texture Types
        ROUGH = new TextureType("rough");
        REGULAR = new TextureType("regular");
        SHINY = new TextureType("shiny");
        FUEL = new TextureType("fuel");
        PENTAGONAL = new TextureType("pentagonal");
        OCTAGONAL = new TextureType("octagonal");
        CRYSTAL = new TextureType("crystal");
        SHARP = new TextureType("sharp");
        FINE = new TextureType("fine");
        TRANSPARENT_FLUID = new TextureType("transparent");
        OPAQUE_FLUID = new TextureType("opaque");

        //Object Types
        DUST = new ItemType("dust", mat -> mat instanceof DustMaterial).setBucketVolume(144).build();
        INGOT = new ItemType("ingot", mat -> mat instanceof IngotMaterial).setBucketVolume(144).build();
        NUGGET = new ItemType("nugget", mat -> mat instanceof IngotMaterial).setBucketVolume(16).build();
        BLOCK = new BlockType("storage_block", mat -> mat instanceof DustMaterial,
                Block.Properties.create(net.minecraft.block.material.Material.IRON).sound(SoundType.METAL)).setBucketVolume(1296).build();
        ORE = new BlockType("ore", mat -> mat instanceof IngotMaterial && mat.hasTag(HAS_ORE),
                Block.Properties.create(net.minecraft.block.material.Material.ROCK).sound(SoundType.STONE)).addTypeTag(USES_UNREFINED_COLOR)
                .setBucketVolume(144).addTypeTag(HAS_NO_FUEL_VALUE).build();
        GEM = new ItemType("gem", mat -> mat instanceof GemMaterial).setBucketVolume(144).build();

        //Materials
        COAL = new GemMaterial("coal", FUEL, 0x1a1a1a, 0).setStandardBurnTime(1600).setElement(Elements.CARBON).addTags(HAS_ORE).build();
        CHARCOAL = new GemMaterial("charcoal", FUEL, 0x443e33, 0).setStandardBurnTime(1600).setElement(Elements.CARBON).build();
        IRON = new IngotMaterial("iron", ROUGH, -1, 1).setElement(Elements.IRON).setUnrefinedColor(0x947664).setItemTier(ItemTier.IRON).setArmorMaterial(ArmorMaterial.IRON).addTags(HAS_ORE).build();
        GOLD = new IngotMaterial("gold", SHINY, 0xfad64a, 2).setElement(Elements.GOLD).setItemTier(ItemTier.GOLD).setArmorMaterial(ArmorMaterial.GOLD).addTags(HAS_ORE).build();
        LAPIS = new GemMaterial("lapis", REGULAR, 0x2351be, 0).addTags(HAS_ORE).build();
        QUARTZ = new GemMaterial("quartz", CRYSTAL, 0xe8dfd0, 0).addTags(HAS_ORE, BLOCK_FROM_4X4).build();
        DIAMOND = new GemMaterial("diamond", PENTAGONAL, 0x34ebe3, 2).setElement(Elements.CARBON).setItemTier(ItemTier.DIAMOND).setArmorMaterial(ArmorMaterial.DIAMOND).build();
        EMERALD = new GemMaterial("emerald", OCTAGONAL, 0x08ad2c, 2).addTags(HAS_ORE).build();
        REDSTONE = new DustMaterial("redstone", REGULAR, 0xfc1a19, 1).addTags(HAS_ORE).build();
        STONE = new DustMaterial("stone", FINE, 0xb1b0ae, 0).setItemTier(ItemTier.STONE).build();
        WOODEN = new DustMaterial("wooden", FINE, 0x87672c, -1).setStandardBurnTime(200).setItemTier(ItemTier.WOOD).build();
        GLOWSTONE = new DustMaterial("glowstone", REGULAR, 0xfcbe60, 1).addTags(BLOCK_FROM_4X4).build();
        BRICK = new IngotMaterial("brick", REGULAR, 0xb55c42, 1).addTags(BLOCK_FROM_4X4).blacklistTypes(NUGGET).setCompoundType(CompoundType.CHEMICAL).build();
        NETHER_BRICK = new IngotMaterial("nether_brick", REGULAR, 0x472a30, 1).addTags(BLOCK_FROM_4X4).blacklistTypes(NUGGET).setCompoundType(CompoundType.CHEMICAL).build();
        FLINT = new GemMaterial("flint", SHARP, 0x222020, 0).blacklistTypes(BLOCK).build();
        OBSIDIAN = new DustMaterial("obsidian", FINE, 0x3c2a53, 3).build();
        BONE = new DustMaterial("bone", REGULAR, 0xfcfaed, 0).addTags(ROD_TO_3_DUST).build();
        BLAZE = new DustMaterial("bone", REGULAR, 0xffc20c, 0).addTags(ROD_TO_3_DUST).build();
        WATER = new FluidMaterial("water", TRANSPARENT_FLUID, 0x3e4ac6).build();
        LAVA = new FluidMaterial("lava", OPAQUE_FLUID, 0xc54c13).build();

        MaterialItems.addItem(COAL, GEM, Items.COAL);
        MaterialBlocks.addBlock(COAL, BLOCK, Blocks.COAL_BLOCK);
        MaterialBlocks.addBlock(COAL, ORE, Blocks.COAL_ORE);

        MaterialItems.addItem(CHARCOAL, GEM, Items.CHARCOAL);

        MaterialItems.addItem(IRON, INGOT, Items.IRON_INGOT);
        MaterialItems.addItem(IRON, NUGGET, Items.IRON_NUGGET);
        MaterialBlocks.addBlock(IRON, BLOCK, Blocks.IRON_BLOCK);
        MaterialBlocks.addBlock(IRON, ORE, Blocks.IRON_ORE);

        MaterialItems.addItem(GOLD, INGOT, Items.GOLD_INGOT);
        MaterialItems.addItem(GOLD, NUGGET, Items.GOLD_NUGGET);
        MaterialBlocks.addBlock(GOLD, BLOCK, Blocks.GOLD_BLOCK);
        MaterialBlocks.addBlock(GOLD, ORE, Blocks.GOLD_ORE);

        MaterialItems.addItem(LAPIS, GEM, Items.LAPIS_LAZULI);
        MaterialBlocks.addBlock(LAPIS, BLOCK, Blocks.LAPIS_BLOCK);
        MaterialBlocks.addBlock(LAPIS, ORE, Blocks.LAPIS_ORE);

        MaterialItems.addItem(DIAMOND, GEM, Items.DIAMOND);
        MaterialBlocks.addBlock(DIAMOND, BLOCK, Blocks.DIAMOND_BLOCK);
        MaterialBlocks.addBlock(DIAMOND, ORE, Blocks.DIAMOND_ORE);

        MaterialItems.addItem(EMERALD, GEM, Items.EMERALD);
        MaterialBlocks.addBlock(EMERALD, BLOCK, Blocks.EMERALD_BLOCK);
        MaterialBlocks.addBlock(EMERALD, ORE, Blocks.EMERALD_ORE);

        MaterialItems.addItem(QUARTZ, GEM, Items.QUARTZ);
        MaterialBlocks.addBlock(QUARTZ, BLOCK, Blocks.QUARTZ_BLOCK);
        MaterialBlocks.addBlock(QUARTZ, ORE, Blocks.NETHER_QUARTZ_ORE);

        MaterialItems.addItem(REDSTONE, DUST, Items.REDSTONE);
        MaterialBlocks.addBlock(REDSTONE, BLOCK, Blocks.REDSTONE_BLOCK);
        MaterialBlocks.addBlock(REDSTONE, ORE, Blocks.REDSTONE_ORE);

        MaterialItems.addItem(GLOWSTONE, DUST, Items.GLOWSTONE_DUST);
        MaterialBlocks.addBlock(GLOWSTONE, BLOCK, Blocks.GLOWSTONE);

        MaterialItems.addItem(BRICK, INGOT, Items.BRICK);
        MaterialBlocks.addBlock(BRICK, BLOCK, Blocks.BRICKS);

        MaterialItems.addItem(NETHER_BRICK, INGOT, Items.NETHER_BRICK);
        MaterialBlocks.addBlock(NETHER_BRICK, BLOCK, Blocks.NETHER_BRICKS);

        MaterialItems.addItem(FLINT, GEM, Items.FLINT);

        MaterialItems.addItem(BONE, DUST, Items.BONE_MEAL);
        MaterialBlocks.addBlock(BONE, BLOCK, Blocks.BONE_BLOCK);

        MaterialItems.addItem(BLAZE, DUST, Items.BLAZE_POWDER);
    }
}