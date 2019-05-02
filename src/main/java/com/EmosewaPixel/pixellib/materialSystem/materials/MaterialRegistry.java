package com.EmosewaPixel.pixellib.materialSystem.materials;

import com.EmosewaPixel.pixellib.materialSystem.lists.MaterialBlocks;
import com.EmosewaPixel.pixellib.materialSystem.lists.MaterialItems;
import com.EmosewaPixel.pixellib.materialSystem.types.BlockType;
import com.EmosewaPixel.pixellib.materialSystem.types.ItemType;
import com.EmosewaPixel.pixellib.materialSystem.types.ObjectType;
import com.EmosewaPixel.pixellib.materialSystem.types.TextureType;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

public class MaterialRegistry {
    public static TextureType ROUGH, REGULAR, SHINY;

    public static Material IRON, GOLD, LAPIS, QUARTZ, DIAMOND, PRISMARINE, EMERALD, REDSTONE;

    public static ObjectType DUST, INGOT, NUGGET, BLOCK, ORE, PICKAXE, SHOVEL, AXE, SWORD, HOE, GEM;

    static {
        //Texture Types
        ROUGH = new TextureType("rough");
        REGULAR = new TextureType("regular");
        SHINY = new TextureType("shiny");

        //Materials
        IRON = new IngotMaterial("iron", ROUGH, 0xd8d8d8).hasOre().build();
        GOLD = new IngotMaterial("gold", SHINY, 0xfad64a).hasOre().build();
        LAPIS = new GemMaterial("lapis", REGULAR, 0x2351be).build();
        QUARTZ = new GemMaterial("quartz", REGULAR, 0xe8dfd0).build();
        DIAMOND = new GemMaterial("diamond", REGULAR, 0x34ebe3).build();
        EMERALD = new GemMaterial("emerald", REGULAR, 0x08ad2c).build();
        REDSTONE = new DustMaterial("redstone", REGULAR, 0xfc1a19).hasOre().build();

        //Object Types
        DUST = new ItemType("dust", mat -> mat instanceof DustMaterial);
        INGOT = new ItemType("ingot", mat -> mat instanceof IngotMaterial);
        NUGGET = new ItemType("nugget", mat -> mat instanceof IngotMaterial);
        BLOCK = new BlockType("storage_block", mat -> {
            if (mat instanceof DustMaterial)
                return ((DustMaterial) mat).getHarvestTier() != null;
            return false;
        }, Block.Properties.create(net.minecraft.block.material.Material.IRON).sound(SoundType.METAL));
        ORE = new BlockType("ore", mat -> {
            if (mat instanceof IngotMaterial && mat.doesHaveOre())
                return ((IngotMaterial) mat).getHarvestTier() != null;
            return false;
        }, Block.Properties.create(net.minecraft.block.material.Material.ROCK).sound(SoundType.STONE));
        PICKAXE = new ItemType("pickaxe", mat -> false);
        AXE = new ItemType("axe", mat -> false);
        SHOVEL = new ItemType("shovel", mat -> false);
        SWORD = new ItemType("sword", mat -> false);
        HOE = new ItemType("hoe", mat -> false);
        GEM = new ItemType("gem", mat -> mat instanceof GemMaterial);

        //Giving vanilla items material values
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

        MaterialItems.addItem(PRISMARINE, GEM, Items.PRISMARINE_CRYSTALS);
        MaterialBlocks.addBlock(PRISMARINE, BLOCK, Blocks.PRISMARINE);

        MaterialItems.addItem(REDSTONE, DUST, Items.REDSTONE);
        MaterialBlocks.addBlock(REDSTONE, BLOCK, Blocks.REDSTONE_BLOCK);
        MaterialBlocks.addBlock(REDSTONE, ORE, Blocks.REDSTONE_ORE);
    }
}