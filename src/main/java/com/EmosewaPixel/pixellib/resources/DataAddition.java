package com.EmosewaPixel.pixellib.resources;

import com.EmosewaPixel.pixellib.materialsystem.MaterialRegistry;
import com.EmosewaPixel.pixellib.materialsystem.lists.MaterialBlocks;
import com.EmosewaPixel.pixellib.materialsystem.lists.MaterialItems;
import com.EmosewaPixel.pixellib.materialsystem.lists.Materials;
import com.EmosewaPixel.pixellib.materialsystem.materials.IMaterialItem;
import com.EmosewaPixel.pixellib.materialsystem.materials.IngotMaterial;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

//This claass is used for registering the default data added by Pixel Lib
public class DataAddition {
    public static void register() {
        //Tags
        MaterialItems.getAllItems().stream().filter(item -> item instanceof IMaterialItem).forEach(item -> TagMaps.addMatItemToTag((IMaterialItem) item));
        MaterialBlocks.getAllBlocks().stream().filter(block -> block instanceof IMaterialItem).forEach(block -> TagMaps.addMatItemToTag((IMaterialItem) block));
        TagMaps.addItemToTag("gems/coal", Items.COAL);
        TagMaps.addItemToTag("gems", Items.COAL);
        TagMaps.addBlockToTag("storage_blocks/brick", Blocks.BRICKS);
        TagMaps.addBlockToTag("storage_blocks/nether_brick", Blocks.NETHER_BRICKS);
        TagMaps.addBlockToTag("storage_blocks/bone", Blocks.BONE_BLOCK);
        TagMaps.addBlockToTag("storage_blocks", Blocks.BRICKS);
        TagMaps.addBlockToTag("storage_blocks", Blocks.NETHER_BRICKS);
        TagMaps.addBlockToTag("storage_blocks", Blocks.BONE_BLOCK);
        TagMaps.addItemToTag("dusts/bone", Items.BONE_MEAL);
        TagMaps.addItemToTag("dusts/blaze", Items.BLAZE_POWDER);
        TagMaps.addItemToTag("dusts", Items.BONE_MEAL);
        TagMaps.addItemToTag("dusts", Items.BLAZE_POWDER);

        //Recipes
        Materials.getAll().stream().filter(mat -> mat instanceof IngotMaterial).forEach(mat -> {
            if (mat.hasTag(MaterialRegistry.BLOCK_FROM_4X4)) {
                if (MaterialBlocks.getBlock(mat, MaterialRegistry.BLOCK) instanceof IMaterialItem)
                    RecipeMaker.addShapedRecipe(location(mat.getName() + "_block"), new ItemStack(MaterialBlocks.getBlock(mat, MaterialRegistry.BLOCK)), "II", "II", 'I', mat.getTag(MaterialRegistry.INGOT));
                if (MaterialItems.getItem(mat, MaterialRegistry.INGOT) instanceof IMaterialItem)
                    RecipeMaker.addShapelessRecipe(location(mat.getName() + "_ingot_from_block"), mat.getName() + "_ingot", new ItemStack(MaterialItems.getItem(mat, MaterialRegistry.INGOT), 4), mat.getTag(MaterialRegistry.BLOCK));
            } else {
                if (MaterialBlocks.getBlock(mat, MaterialRegistry.BLOCK) instanceof IMaterialItem)
                    RecipeMaker.addShapedRecipe(location(mat.getName() + "_block"), new ItemStack(MaterialBlocks.getBlock(mat, MaterialRegistry.BLOCK)), "III", "III", "III", 'I', mat.getTag(MaterialRegistry.INGOT));
                if (MaterialItems.getItem(mat, MaterialRegistry.INGOT) instanceof IMaterialItem)
                    RecipeMaker.addShapelessRecipe(location(mat.getName() + "_ingot_from_block"), mat.getName() + "_ingot", new ItemStack(MaterialItems.getItem(mat, MaterialRegistry.INGOT), 9), mat.getTag(MaterialRegistry.BLOCK));
            }
            if (MaterialItems.getItem(mat, MaterialRegistry.INGOT) instanceof IMaterialItem && MaterialItems.contains(mat, MaterialRegistry.NUGGET))
                RecipeMaker.addShapedRecipe(location(mat.getName() + "ingot_from_nuggets"), mat.getName() + "_ingot", new ItemStack(MaterialItems.getItem(mat, MaterialRegistry.INGOT)), "NNN", "NNN", "NNN", 'N', mat.getTag(MaterialRegistry.NUGGET));
            if (!mat.hasTag(MaterialRegistry.DISABLE_SIMPLE_PROCESSING))
                RecipeMaker.addFurnaceRecipe(location(mat.getName() + "_ingot"), mat.getTag(MaterialRegistry.DUST), MaterialItems.getItem(mat, MaterialRegistry.INGOT));
            if (MaterialItems.getItem(mat, MaterialRegistry.NUGGET) instanceof IMaterialItem)
                RecipeMaker.addShapelessRecipe(location(mat.getName() + "_nuggets"), new ItemStack(MaterialItems.getItem(mat, MaterialRegistry.NUGGET), 9), mat.getTag(MaterialRegistry.INGOT));
        });
    }

    private static ResourceLocation location(String name) {
        return new ResourceLocation("pixellib", name);
    }
}