package com.EmosewaPixel.pixellib.jei;

import com.EmosewaPixel.pixellib.PixelLib;
import com.EmosewaPixel.pixellib.blocks.BlockRegistry;
import com.EmosewaPixel.pixellib.jei.categories.*;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class JEIPlugIn implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(PixelLib.ModId);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration reg) {
        IGuiHelper helper = reg.getJeiHelpers().getGuiHelper();
        reg.addRecipeCategories(new AlloyerCategory(helper), new BlastFurnaceCategory(helper), new CokeOvenCategory(helper), new CrusherCategory(helper), new InfusionCategory(helper), new PressingCategory(helper), new SawmillCategory(helper));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration reg) {
        reg.addRecipeCatalyst(new ItemStack(BlockRegistry.ALLOYER), new ResourceLocation("pixellib:alloyer"));
        reg.addRecipeCatalyst(new ItemStack(BlockRegistry.BLAST_FURNACE), new ResourceLocation("pixellib:blast_furnace"));
        reg.addRecipeCatalyst(new ItemStack(BlockRegistry.COKE_OVEN), new ResourceLocation("pixellib:coke_oven"));
        reg.addRecipeCatalyst(new ItemStack(BlockRegistry.CRUSHER), new ResourceLocation("pixellib:crusher"));
        reg.addRecipeCatalyst(new ItemStack(BlockRegistry.INFUSION_TABLE), new ResourceLocation("pixellib:infusion"));
        reg.addRecipeCatalyst(new ItemStack(Blocks.PISTON), new ResourceLocation("pixellib:pressing"));
        reg.addRecipeCatalyst(new ItemStack(BlockRegistry.SAWMILL), new ResourceLocation("pixellib:sawmill"));
    }

    @Override
    public void registerRecipes(IRecipeRegistration reg) {
        reg.addRecipes(RecipeTypes.alloyerRecipes, new ResourceLocation("pixellib:alloyer"));
        reg.addRecipes(RecipeTypes.blastFurnaceRecipes, new ResourceLocation("pixellib:blast_furnace"));
        reg.addRecipes(RecipeTypes.cokeOvenRecipes, new ResourceLocation("pixellib:coke_oven"));
        reg.addRecipes(RecipeTypes.crusherRecipes, new ResourceLocation("pixellib:crusher"));
        reg.addRecipes(RecipeTypes.infusionRecipes, new ResourceLocation("pixellib:infusion"));
        reg.addRecipes(RecipeTypes.pressingRecipes, new ResourceLocation("pixellib:pressing"));
        reg.addRecipes(RecipeTypes.sawmillRecipes, new ResourceLocation("pixellib:sawmill"));
    }
}