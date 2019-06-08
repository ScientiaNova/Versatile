package com.EmosewaPixel.pixellib.materialSystem.materials;

import com.EmosewaPixel.pixellib.materialSystem.lists.MaterialBlocks;
import com.EmosewaPixel.pixellib.materialSystem.lists.MaterialItems;
import com.EmosewaPixel.pixellib.materialSystem.types.BlockType;
import com.EmosewaPixel.pixellib.materialSystem.types.ItemType;
import com.EmosewaPixel.pixellib.materialSystem.types.ObjectType;
import com.EmosewaPixel.pixellib.materialSystem.types.TextureType;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;

public class MaterialRegistry {
    public static TextureType ROUGH, REGULAR, SHINY, FUEL, PENTAGONAL, OCTAGONAL, CRYSTAL, SHARP;

    public static Material IRON, GOLD, LAPIS, QUARTZ, DIAMOND, EMERALD, REDSTONE, STONE, WOODEN, COAL, GLOWSTONE, BRICK, FLINT;

    public static ObjectType DUST, INGOT, NUGGET, BLOCK, ORE, GEM;

    public static MaterialTag HAS_ORE = new MaterialTag("has_ore");
    public static MaterialTag DISABLE_SIMPLE_PROCESIING = new MaterialTag("disable_simple_processing");
    public static MaterialTag BLOCK_FROM_4X4 = new MaterialTag("block_from_4x4");

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
        DUST = new ItemType("dust", mat -> mat instanceof DustMaterial);
        INGOT = new ItemType("ingot", mat -> mat instanceof IngotMaterial);
        NUGGET = new ItemType("nugget", mat -> mat instanceof IngotMaterial);
        BLOCK = new BlockType("storage_block", mat -> mat instanceof DustMaterial,
                Block.Properties.create(net.minecraft.block.material.Material.IRON).sound(SoundType.METAL));
        ORE = new BlockType("ore", mat -> mat instanceof IngotMaterial && mat.hasTag(HAS_ORE),
                Block.Properties.create(net.minecraft.block.material.Material.ROCK).sound(SoundType.STONE));
        GEM = new ItemType("gem", mat -> mat instanceof GemMaterial);

        //Materials
        COAL = new DustMaterial("coal", FUEL, 0x1a1a1a, 0).addTags(HAS_ORE).build();
        IRON = new IngotMaterial("iron", ROUGH, 0xececec, 1).setItemTier(ItemTier.IRON).setArmorMaterial(ArmorMaterial.IRON).addTags(HAS_ORE).build();
        GOLD = new IngotMaterial("gold", SHINY, 0xfad64a, 2).setItemTier(ItemTier.GOLD).setArmorMaterial(ArmorMaterial.GOLD).addTags(HAS_ORE).build();
        LAPIS = new GemMaterial("lapis", REGULAR, 0x2351be, 0).addTags(HAS_ORE).build();
        QUARTZ = new GemMaterial("quartz", CRYSTAL, 0xe8dfd0, 0).addTags(HAS_ORE, BLOCK_FROM_4X4).build();
        DIAMOND = new GemMaterial("diamond", PENTAGONAL, 0x34ebe3, 2).setItemTier(ItemTier.DIAMOND).setArmorMaterial(ArmorMaterial.DIAMOND).build();
        EMERALD = new GemMaterial("emerald", OCTAGONAL, 0x08ad2c, 2).addTags(HAS_ORE).build();
        REDSTONE = new DustMaterial("redstone", REGULAR, 0xfc1a19, 1).addTags(HAS_ORE).build();
        STONE = new Material("stone", REGULAR, 0xcccccc, 0).setItemTier(ItemTier.STONE).build();
        WOODEN = new Material("wooden", REGULAR, 0xd5bc77, -1).setItemTier(ItemTier.WOOD).build();
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