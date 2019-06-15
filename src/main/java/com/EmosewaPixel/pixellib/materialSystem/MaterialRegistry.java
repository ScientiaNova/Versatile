package com.EmosewaPixel.pixellib.materialSystem;

import com.EmosewaPixel.pixellib.materialSystem.element.Element;
import com.EmosewaPixel.pixellib.materialSystem.lists.MaterialBlocks;
import com.EmosewaPixel.pixellib.materialSystem.lists.MaterialItems;
import com.EmosewaPixel.pixellib.materialSystem.materials.DustMaterial;
import com.EmosewaPixel.pixellib.materialSystem.materials.GemMaterial;
import com.EmosewaPixel.pixellib.materialSystem.materials.IngotMaterial;
import com.EmosewaPixel.pixellib.materialSystem.materials.Material;
import com.EmosewaPixel.pixellib.materialSystem.types.BlockType;
import com.EmosewaPixel.pixellib.materialSystem.types.ItemType;
import com.EmosewaPixel.pixellib.materialSystem.types.ObjectType;
import com.EmosewaPixel.pixellib.materialSystem.types.TextureType;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;

public class MaterialRegistry {
    public static TextureType ROUGH, REGULAR, SHINY, FUEL, PENTAGONAL, OCTAGONAL, CRYSTAL, SHARP;

    public static Material IRON, GOLD, LAPIS, QUARTZ, DIAMOND, EMERALD, REDSTONE, STONE, WOODEN, COAL, GLOWSTONE, BRICK, FLINT;

    public static ObjectType DUST, INGOT, NUGGET, BLOCK, ORE, GEM;

    public static final String HAS_ORE = "has_ore";
    public static final String DISABLE_SIMPLE_PROCESSING = "disable_simple_processing";
    public static final String BLOCK_FROM_4X4 = "block_from_4x4";
    public static final String IS_GAS = "is_gas";

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

        //Object Types
        DUST = new ItemType("dust", mat -> mat instanceof DustMaterial).setBucketVolume(144);
        INGOT = new ItemType("ingot", mat -> mat instanceof IngotMaterial).setBucketVolume(144);
        NUGGET = new ItemType("nugget", mat -> mat instanceof IngotMaterial).setBucketVolume(16);
        BLOCK = new BlockType("storage_block", mat -> mat instanceof DustMaterial,
                Block.Properties.create(net.minecraft.block.material.Material.IRON).sound(SoundType.METAL)).setBucketVolume(1296);
        ORE = new BlockType("ore", mat -> mat instanceof IngotMaterial && mat.hasTag(HAS_ORE),
                Block.Properties.create(net.minecraft.block.material.Material.ROCK).sound(SoundType.STONE)).addTypeTag(USES_UNREFINED_COLOR).setBucketVolume(144);
        GEM = new ItemType("gem", mat -> mat instanceof GemMaterial).setBucketVolume(144);

        //Materials
        COAL = new DustMaterial("coal", FUEL, 0x1a1a1a, 0).addTags(HAS_ORE).build();
        IRON = new IngotMaterial("iron", ROUGH, -1, 1).setElement(new Element("Fe", 26, 30)).setUnrefinedColor(0x947664).setItemTier(ItemTier.IRON).setArmorMaterial(ArmorMaterial.IRON).addTags(HAS_ORE).build();
        GOLD = new IngotMaterial("gold", SHINY, 0xfad64a, 2).setElement(new Element("Au", 79, 118)).setItemTier(ItemTier.GOLD).setArmorMaterial(ArmorMaterial.GOLD).addTags(HAS_ORE).build();
        LAPIS = new GemMaterial("lapis", REGULAR, 0x2351be, 0).addTags(HAS_ORE).build();
        QUARTZ = new GemMaterial("quartz", CRYSTAL, 0xe8dfd0, 0).addTags(HAS_ORE, BLOCK_FROM_4X4).build();
        DIAMOND = new GemMaterial("diamond", PENTAGONAL, 0x34ebe3, 2).setItemTier(ItemTier.DIAMOND).setArmorMaterial(ArmorMaterial.DIAMOND).build();
        EMERALD = new GemMaterial("emerald", OCTAGONAL, 0x08ad2c, 2).addTags(HAS_ORE).build();
        REDSTONE = new DustMaterial("redstone", REGULAR, 0xfc1a19, 1).addTags(HAS_ORE).build();
        STONE = new DustMaterial("stone", REGULAR, 0xcccccc, 0).setItemTier(ItemTier.STONE).blacklistTypes(BLOCK).build();
        WOODEN = new DustMaterial("wooden", REGULAR, 0xd5bc77, -1).setItemTier(ItemTier.WOOD).blacklistTypes(BLOCK).build();
        GLOWSTONE = new DustMaterial("glowstone", REGULAR, 0xfcbe60, 1).addTags(BLOCK_FROM_4X4).build();
        BRICK = new IngotMaterial("brick", REGULAR, 0xb55c42, 1).addTags(BLOCK_FROM_4X4).blacklistTypes(NUGGET).build();
        FLINT = new GemMaterial("flint", SHARP, 0x222020, -1).blacklistTypes(BLOCK).build();

        MaterialItems.addItem(COAL, GEM, Items.COAL);
        MaterialBlocks.addBlock(COAL, BLOCK, Blocks.COAL_BLOCK);
        MaterialBlocks.addBlock(COAL, ORE, Blocks.COAL_ORE);

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

        MaterialItems.addItem(FLINT, GEM, Items.FLINT);
    }
}