package com.EmosewaPixel.pixellib.resourceAddition;

import com.EmosewaPixel.pixellib.materialSystem.lists.MaterialBlocks;
import com.EmosewaPixel.pixellib.materialSystem.lists.MaterialItems;
import com.EmosewaPixel.pixellib.materialSystem.lists.Materials;
import com.EmosewaPixel.pixellib.materialSystem.materials.IMaterialItem;
import com.EmosewaPixel.pixellib.materialSystem.materials.IngotMaterial;
import com.EmosewaPixel.pixellib.materialSystem.materials.Material;
import com.EmosewaPixel.pixellib.materialSystem.materials.MaterialRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class DataAddition {
    public static void register() {
        //Tags
        for (Item item : MaterialItems.getAllItems())
            if (item instanceof IMaterialItem)
                TagMaps.addMatItemToTag((IMaterialItem) item);
        for (Block block : MaterialBlocks.getAllBlocks())
            if (block instanceof IMaterialItem)
                TagMaps.addMatItemToTag((IMaterialItem) block);
        TagMaps.addItemToTag("gems/coal", Items.COAL);
        TagMaps.addItemToTag("gems", Items.COAL);

        //Recipes
        for (Material mat : Materials.getAll()) {
            if (mat instanceof IngotMaterial) {
                if (MaterialBlocks.getBlock(mat, MaterialRegistry.BLOCK) instanceof IMaterialItem)
                    RecipeInjector.addShapedRecipe(location(mat.getName() + "_block"), new ItemStack(MaterialBlocks.getBlock(mat, MaterialRegistry.BLOCK)), "III", "III", "III", 'I', mat.getTag(MaterialRegistry.INGOT));
                if (MaterialItems.getItem(mat, MaterialRegistry.INGOT) instanceof IMaterialItem) {
                    RecipeInjector.addShapelessRecipe(location(mat.getName() + "_ingot_from_block"), mat.getName() + "_ingot", new ItemStack(MaterialItems.getItem(mat, MaterialRegistry.INGOT), 9), mat.getTag(MaterialRegistry.BLOCK));
                    RecipeInjector.addShapedRecipe(location(mat.getName() + "ingot_from_nuggets"), mat.getName() + "_ingot", new ItemStack(MaterialItems.getItem(mat, MaterialRegistry.INGOT)), "NNN", "NNN", "NNN", 'N', mat.getTag(MaterialRegistry.NUGGET));
                }
                if (((IngotMaterial) mat).hasSimpleProcessing())
                    RecipeInjector.addFurnaceRecipe(location(mat.getName() + "_ingot"), mat.getTag(MaterialRegistry.DUST), new ItemStack(MaterialItems.getItem(mat, MaterialRegistry.INGOT)));
                if (MaterialItems.getItem(mat, MaterialRegistry.NUGGET) instanceof IMaterialItem)
                    RecipeInjector.addShapelessRecipe(location(mat.getName() + "_nuggets"), new ItemStack(MaterialItems.getItem(mat, MaterialRegistry.NUGGET), 9), mat.getTag(MaterialRegistry.INGOT));
            }
        }
    }

    private static ResourceLocation location(String name) {
        return new ResourceLocation("pixellib", name);
    }
}